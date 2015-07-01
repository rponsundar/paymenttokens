/**
 * 
 */
package com.bank.psh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.psh.beans.CardInfoRequestBean;
import com.bank.psh.beans.ClientRegistrationRequest;
import com.bank.psh.beans.ClientRegistrationResponse;
import com.bank.psh.beans.TokenInfoResponseBean;
import com.bank.psh.beans.URNRequestBean;
import com.bank.psh.beans.URNResponseBean;

/**
 * @author user
 *
 */
public interface PaymentServiceHubController {

	/**
	 * Provides a unique reference number requested by the customer
	 * 
	 * @param request
	 * @param response
	 * @param cardDetails
	 * @return URNResponseBean
	 */
	URNResponseBean getUniqueRefNumber(HttpServletRequest request,
			HttpServletResponse response, CardInfoRequestBean cardDetails);

	/**
	 * Provide token associated with the unique reference number.
	 * 
	 * @param request
	 * @param response
	 * @param urnRequest
	 * @return TokenInfoResponseBean
	 */
	TokenInfoResponseBean getToken(HttpServletRequest request,
			HttpServletResponse response, URNRequestBean urnRequest);
	
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @param urnRequest
	 * @return 	ClientRegistrationResponse
	 */
	ClientRegistrationResponse registerClient(HttpServletRequest request,
			HttpServletResponse response, ClientRegistrationRequest urnRequest);




   List<String> getCustomerCards(String custId);
}
