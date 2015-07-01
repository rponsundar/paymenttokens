/**
 * 
 */
package com.bank.psh.beans;

/**
 * @author user
 *
 */
public class TokenInfoResponseBean extends ResponseBean {
	
	private String token;
	private String cryto;

	public String getCryto() {
		return cryto;
	}

	public void setCryto(String cryto) {
		this.cryto = cryto;
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

}
