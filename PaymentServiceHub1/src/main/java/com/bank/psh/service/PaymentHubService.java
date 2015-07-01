/**
 * 
 */
package com.bank.psh.service;

import java.util.ArrayList;

import com.bank.psh.beans.CardInfoRequestBean;
import com.bank.psh.beans.CardURNInfoResponseBean;
import com.bank.psh.beans.ClientRegistrationRequest;
import com.bank.psh.beans.TokenInfoResponseBean;
import com.bank.psh.beans.URNRequestBean;
import com.bank.psh.beans.URNResponseBean;
import com.bank.psh.beans.UserBean;
import com.bank.psh.controller.model.ClientDetails;

/**
 * @author user
 *
 */
public interface PaymentHubService {

	/**
	 * Provides a unique reference number requested by the customer
	 * 
	 * @param userBean
	 * @param cardDetails
	 * @return URNResponseBean
	 */
	URNResponseBean getUniqueRefNumber(UserBean userBean,
			CardInfoRequestBean cardDetails);

	/**
	 * Provide token associated with the unique reference number.
	 * 
	 * @param userBean
	 * @param urnRequest
	 * @return TokenInfoResponseBean
	 */
	TokenInfoResponseBean getToken(UserBean userBean, URNRequestBean urnRequest);
	
	
	/**
	 * Provide token associated with the unique reference number.
	 * 
	 * @param userBean
	 * @param urnRequest
	 * @return TokenInfoResponseBean
	 */
	ClientDetails registerClient(ClientRegistrationRequest registrationRequest);
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	boolean validateUser(UserBean user);
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	ArrayList<String> getCustomerCards(String custId);
	
	/**
	 * Expire Token and URN after it is used
	 * 
	 * @param token
	 * @return
	 */
	String expireTokenURN(String token);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ArrayList<CardURNInfoResponseBean> getCardURNInfo (String userId);

}
