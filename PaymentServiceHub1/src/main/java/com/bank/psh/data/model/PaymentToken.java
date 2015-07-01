/**
 * 
 */
package com.bank.psh.data.model;

import java.sql.Date;

/**
 * @author user
 *
 */
public class PaymentToken {
	
	private int id;
	private String token;
	private Date expDate;
	private String last4Digit;
	private String cardId;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the expDate
	 */
	public Date getExpDate() {
		return expDate;
	}
	/**
	 * @param expDate the expDate to set
	 */
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	/**
	 * @return the last4Digit
	 */
	public String getLast4Digit() {
		return last4Digit;
	}
	/**
	 * @param last4Digit the last4Digit to set
	 */
	public void setLast4Digit(String last4Digit) {
		this.last4Digit = last4Digit;
	}
	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}
	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}