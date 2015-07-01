package com.provider.paymenttoken.beans;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PaymentTokenRequestBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String versionNo;
	
	@NotNull
	@Min(1)
	private Integer tokenRequestorId;
	
	private Integer panLength;
	
	@NotNull
	@Size(min=16, max=16, message="Pan number should be 16 digits in length")
	private String panNo;
	
    @NotNull(message="PAN Expiry Date can not be null")
    @Size(min=6, max=8, message="Expiry Date is invalid")
	private String panExpiryDate;

	private Integer tokenLocation;

	private Integer accountVerification;
    
	private Integer cardHolderDataLength;

	private Integer deviceInfoLength;
	
	
	public PaymentTokenRequestBean(){
		
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public Integer getTokenRequestorId() {
		return tokenRequestorId;
	}
	public void setTokenRequestorId(Integer tokenRequestorId) {
		this.tokenRequestorId = tokenRequestorId;
	}
	public Integer getPanLength() {
		return panLength;
	}
	public void setPanLength(Integer panLength) {
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
	public Integer getTokenLocation() {
		return tokenLocation;
	}
	public void setTokenLocation(Integer tokenLocation) {
		this.tokenLocation = tokenLocation;
	}
	public Integer getAccountVerification() {
		return accountVerification;
	}
	public void setAccountVerification(Integer accountVerification) {
		this.accountVerification = accountVerification;
	}
	public Integer getCardHolderDataLength() {
		return cardHolderDataLength;
	}
	public void setCardHolderDataLength(Integer cardHolderDataLength) {
		this.cardHolderDataLength = cardHolderDataLength;
	}
	public Integer getDeviceInfoLength() {
		return deviceInfoLength;
	}
	public void setDeviceInfoLength(Integer deviceInfoLength) {
		this.deviceInfoLength = deviceInfoLength;
	}



	
	
	
	

}
