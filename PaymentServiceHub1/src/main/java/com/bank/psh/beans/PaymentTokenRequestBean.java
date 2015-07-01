package com.bank.psh.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PaymentTokenRequestBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public PaymentTokenRequestBean(){
		
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public int getTokenRequestorId() {
		return tokenRequestorId;
	}
	public void setTokenRequestorId(int tokenRequestorId) {
		this.tokenRequestorId = tokenRequestorId;
	}
	public int getPanLength() {
		return panLength;
	}
	public void setPanLength(int panLength) {
		this.panLength = panLength;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getPanExpiryDate() {
		return panExpiryDate;
	}
	public void setPanExpiryDate(String panExpiryDate) {
		this.panExpiryDate = panExpiryDate;
	}
	public int getTokenLocation() {
		return tokenLocation;
	}
	public void setTokenLocation(int tokenLocation) {
		this.tokenLocation = tokenLocation;
	}
	public int getAccountVerification() {
		return accountVerification;
	}
	public void setAccountVerification(int accountVerification) {
		this.accountVerification = accountVerification;
	}
	public int getCardHolderDataLength() {
		return cardHolderDataLength;
	}
	public void setCardHolderDataLength(int cardHolderDataLength) {
		this.cardHolderDataLength = cardHolderDataLength;
	}
	public int getDeviceInfoLength() {
		return deviceInfoLength;
	}
	public void setDeviceInfoLength(int deviceInfoLength) {
		this.deviceInfoLength = deviceInfoLength;
	}
	private String versionNo;
	private int tokenRequestorId;
	private int panLength;
	private String panNo;
	private String panExpiryDate;
	private int tokenLocation;
	private int accountVerification;
	private int cardHolderDataLength;
	private int deviceInfoLength;


	
	
	
	

}
