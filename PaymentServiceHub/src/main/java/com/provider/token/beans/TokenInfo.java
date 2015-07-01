/**
 * 
 */
package com.provider.token.beans;

/**
 * @author user
 *
 */
public class TokenInfo {

	private String token;
	private String last4Digit;
	private String expDate;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the last4Digit
	 */
	public String getLast4Digit() {
		return last4Digit;
	}

	/**
	 * @param last4Digit
	 *            the last4Digit to set
	 */
	public void setLast4Digit(String last4Digit) {
		this.last4Digit = last4Digit;
	}

	/**
	 * @return the expDate
	 */
	public String getExpDate() {
		return expDate;
	}

	/**
	 * @param expDate
	 *            the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
}