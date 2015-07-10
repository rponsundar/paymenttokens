/**
 * 
 */
package com.bank.psh.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.psh.beans.CardInfoRequestBean;
import com.bank.psh.beans.CardURNInfoResponseBean;
import com.bank.psh.beans.ClientRegistrationRequest;
import com.bank.psh.beans.ClientRegistrationResponse;
import com.bank.psh.beans.TokenInfoResponseBean;
import com.bank.psh.beans.URNRequestBean;
import com.bank.psh.beans.URNResponseBean;
import com.bank.psh.beans.UserBean;
import com.bank.psh.controller.PaymentServiceHubController;
import com.bank.psh.controller.model.ClientDetails;
import com.bank.psh.service.PaymentHubService;

/**
 * @author user
 *
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
@RestController
@RequestMapping("/psh")
public class PaymentServiceHubControllerImpl implements
		PaymentServiceHubController {

	private static final Logger logger = Logger
			.getLogger(PaymentServiceHubControllerImpl.class);

	private PaymentHubService paymentHubService;

	/**
	 * @param paymentHubService
	 *            the paymentHubService to set
	 */
	@Autowired
	public void setPaymentHubService(PaymentHubService paymentHubService) {
		this.paymentHubService = paymentHubService;
	}

	@RequestMapping(value = "/urn", method = RequestMethod.POST)
	public @ResponseBody URNResponseBean getUniqueRefNumber(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody CardInfoRequestBean cardDetails) {
		logger.info("getUniqueRefNumber start");

		URNResponseBean urn = paymentHubService.getUniqueRefNumber(null,
				cardDetails);
		logger.info("getUniqueRefNumber end");
		return urn;
	}

	@RequestMapping(value = "/genpaymenttoken", method = RequestMethod.POST)
	public @ResponseBody String generatePaymentToken(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody CardInfoRequestBean cardRequest) {

		HttpSession session = request.getSession();
		if(cardRequest.getName() == null) {
			logger.info("Token requested for URN " + cardRequest.getCardNum());
			try {
				URNResponseBean bean = paymentHubService.getUniqueRefNumber(null,
						cardRequest);
				session.setAttribute("USER_URN", bean.getUrn());
			} catch (Exception ex) {
				logger.error("Error while generating token " + ex.getMessage());
				return "F";
			}
		} else {
			session.setAttribute("USER_URN", cardRequest.getName());
			logger.info("URN loaded in the session");
		}
		return "S";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String validateUser(HttpServletRequest request,
			HttpServletResponse response, @Valid @RequestBody UserBean user) {
		logger.info("login start");
		boolean isValid = paymentHubService.validateUser(user);
		if (isValid)
			return "true";
		logger.info("login end");
		return "false";
	}

	@RequestMapping(value = "/cards/{user}", method = RequestMethod.GET)
	public @ResponseBody List<String> getCustomerCards(@PathVariable String user) {
		logger.info("getCustomerCards start");
		logger.info("user variable" + user);
		List<String> cards = paymentHubService.getCustomerCards(user);
		logger.info("getCustomerCards end");
		return cards;
	}

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public @ResponseBody TokenInfoResponseBean getToken(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody URNRequestBean urnRequest) {
		logger.info("getToken start");

		TokenInfoResponseBean token = paymentHubService.getToken(null,
				urnRequest);

		logger.info("getToken end");
		return token;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody ClientRegistrationResponse registerClient(
			HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody ClientRegistrationRequest regRequest) {

		System.out.println("Client NME:" + regRequest.getClientName());
		System.out.println("rEDIRECT uri::" + regRequest.getRedirectURI());
		ClientDetails client = paymentHubService.registerClient(regRequest);
		ClientRegistrationResponse srvcResponse = new ClientRegistrationResponse();
		srvcResponse.setClientId(client.getClientId());
		return srvcResponse;
	}

	@RequestMapping(value = "/clientDetails/{clientId}", method = RequestMethod.GET)
	public @ResponseBody ClientDetails getClient(@PathVariable String clientId) {

		logger.info("Client Id: " + clientId);
		ClientDetails clientDetail = paymentHubService
				.getClientDetails(clientId);
		return clientDetail;
	}

	@RequestMapping(value = "/expire", method = RequestMethod.POST)
	public @ResponseBody String expireToken(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String tokenNo) {
		return paymentHubService.expireTokenURN(tokenNo);
	}

	@RequestMapping(value = "/tokenlist", method = RequestMethod.GET)
	public @ResponseBody List<CardURNInfoResponseBean> getTokenList(
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		SecurityContext securityContext = (SecurityContext) session
				.getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication authentication = (Authentication) securityContext
				.getAuthentication();

		logger.info("Card & URN requested for the user "
				+ authentication.getName());

		List<CardURNInfoResponseBean> beanList = paymentHubService.getCardURNInfo(authentication.getName());
		return beanList;
	}


}