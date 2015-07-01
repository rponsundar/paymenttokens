/**
 * 
 */
package com.bank.psh.data.model;

import java.sql.Date;

/**
 * @author user
 *
 */
public class PaymentURN {
	
	private int id;
	private int tokenId;
	private String urn;
	private Date expDate;
	private float monetaryValue;
	
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
	 * @return the tokenId
	 */
	public int getTokenId() {
		return tokenId;
	}
	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	/**
	 * @return the urn
	 */
	public String getUrn() {
		return urn;
	}
	/**
	 * @param urn the urn to set
	 */
	public void setUrn(String urn) {
		this.urn = urn;
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
	 * @return the monetaryValue
	 */
	public float getMonetaryValue() {
		return monetaryValue;
	}
	/**
	 * @param monetaryValue the monetaryValue to set
	 */
	public void setMonetaryValue(float monetaryValue) {
		this.monetaryValue = monetaryValue;
	}
}