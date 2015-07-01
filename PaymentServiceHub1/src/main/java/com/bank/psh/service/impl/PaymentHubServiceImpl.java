/**
 * 
 */
package com.bank.psh.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.bank.psh.beans.CardInfoRequestBean;
import com.bank.psh.beans.CardURNInfoResponseBean;
import com.bank.psh.beans.ClientRegistrationRequest;
import com.bank.psh.beans.TokenInfoResponseBean;
import com.bank.psh.beans.URNRequestBean;
import com.bank.psh.beans.URNResponseBean;
import com.bank.psh.beans.UserBean;
import com.bank.psh.controller.impl.PaymentServiceHubControllerImpl;
import com.bank.psh.controller.model.ClientDetails;
import com.bank.psh.data.dao.PaymentServiceHubDao;
import com.bank.psh.data.model.PaymentToken;
import com.bank.psh.data.model.PaymentURN;
import com.bank.psh.mapper.PaymentHubMapper;
import com.bank.psh.service.AuditService;
import com.bank.psh.service.PaymentHubService;
import com.bank.psh.service.utils.PshUtil;
import com.provider.token.beans.CardInfo;
import com.provider.token.beans.TokenInfo;
import com.provider.token.service.PaymentTokenServiceProvider;

/**
 * @author user
 *
 */
@Component
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PaymentHubServiceImpl implements PaymentHubService {

	private static final Logger logger = Logger
			.getLogger(PaymentServiceHubControllerImpl.class);

	private PaymentHubMapper mapper;

	private PaymentServiceHubDao pshDao;

	private PaymentTokenServiceProvider tokenServiceProvider;
	
	private AuditService auditService;

	/**
	 * @param mapper
	 *            the mapper to set
	 */
	@Autowired
	public void setMapper(PaymentHubMapper mapper) {
		this.mapper = mapper;
	}
	
   
	
	@Autowired
	public void setAuditService(AuditService auditService) {
		this.auditService =  auditService;
	}

	/**
	 * @param tokenServiceProvider
	 *            the tokenServiceProvider to set
	 */
	@Autowired
	public void setTokenServiceProvider(
			PaymentTokenServiceProvider tokenServiceProvider) {
		this.tokenServiceProvider = tokenServiceProvider;
	}

	/**
	 * @param pshDao
	 *            the pshDao to set
	 */
	@Autowired
	public void setPshDao(PaymentServiceHubDao pshDao) {
		this.pshDao = pshDao;
	}

	@Override
	public URNResponseBean getUniqueRefNumber(UserBean userBean,
			CardInfoRequestBean cardDetails) {
		URNResponseBean urnBean = new URNResponseBean();

		logger.info("Generating Token.... -> " + cardDetails);
		CardInfo cardInfo = mapper.mapCardIfo(cardDetails);
		auditService.logAudit("Request initiated for generating payment token");
		TokenInfo token = tokenServiceProvider.generateToken(cardInfo);
		auditService.logAudit("Token Generated successfully -> " + token.getToken());
		logger.info("Token Generated successfully -> " + token.getToken());

		// Generate URN
		logger.info("Generating URN.... -> " + cardDetails);
		auditService.logAudit("Generating URN.... -> " + cardDetails);
		String urn = PshUtil.generateURN(cardInfo);
		urnBean.setUrn(urn);
		auditService.logAudit("URN Generated successfully -> " + urn);
		logger.info("URN Generated successfully -> " + urn);

		// Save token to bank database
		logger.info("Saving generated tokn and URN.........");
		auditService.logAudit("Saving generated token and URN.........");
		PaymentToken paymentToken = mapper.mapPaymentToken(token);
		pshDao.savePaymentToken(paymentToken);
		
		PaymentURN paymentURN = mapper.mapPaymentURN(paymentToken, urn, cardInfo.getAmount());
		pshDao.savePaymentURN(paymentURN);
		
		mapper.mapPaymentToken(token);

		return urnBean;
	}

	@Override
	public TokenInfoResponseBean getToken(UserBean userBean,
			URNRequestBean urnRequest) {
		TokenInfoResponseBean tokenInfo = null;
		
		PaymentToken pmTkn = pshDao.getToken(urnRequest.getUrn());
		tokenInfo = new TokenInfoResponseBean();
		tokenInfo.setToken(pmTkn.getToken());
		auditService.logAudit("Your token ref -> " + tokenInfo.getToken());
		logger.info("Your token ref -> " + tokenInfo.getToken());
		return tokenInfo;
	}
	
	
	@Override
	public ClientDetails registerClient(ClientRegistrationRequest registrationRequest) {
		TokenInfoResponseBean tokenInfo = null;
		SecureRandom random = new SecureRandom();
		String clientId = new BigInteger(52, random).toString(5)
				.toUpperCase();
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setClientId(clientId);
		clientDetails.setRedirectURI(registrationRequest.getRedirectURI());
		clientDetails.setDomain(registrationRequest.getDomain());
		clientDetails.setClientName(registrationRequest.getClientName());
		clientDetails.setAuthorization_grants("client_credentials,authorization_code,implicit,password,refresh_token");
		clientDetails.setAuthorities("ROLE_USER");
		clientDetails.setAccessValidity(180);
		clientDetails.setScope("trust,read,write");
	    clientDetails.setResourceId("PaymentServiceHub");
	    clientDetails.setAutoApproval("true");
	    auditService.logAudit("Request for registering Client ->" + registrationRequest.getClientName() );
	    pshDao.registerClient(clientDetails);
	    auditService.logAudit("Client Registered Successfully ->" + clientId);
		return clientDetails;
	}



	@Override
	public boolean validateUser(UserBean user) {
		// TODO Auto-generated method stub
		return pshDao.validateUser(user);
	}



	@Override
	public ArrayList<String> getCustomerCards(String custId) {
		// TODO Auto-generated method stub
		return pshDao.getCustCards(custId);
	}



	@Override
	public String expireTokenURN(String token) {
		// TODO Auto-generated method stub
		return pshDao.expireTokenURN(token);
	}
	
	@Override
	public ArrayList<CardURNInfoResponseBean> getCardURNInfo (String userId) {
		
		return pshDao.getCustCardURNInfo(userId);
	}
	
	
}
