package com.provider.paymenttoken.beans;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PaymentTokenResponseBean extends ErrorInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String versionNo;
	private int requestStatus;
	private int reasonCodeLength;
	private String reasonCode;
	private int tokenLength;
	private String paymentToken;
	private int tokenReferenceIdLength;
	private String tokenReferenceId;
	private String tokenExpiry;

	
	public PaymentTokenResponseBean(){
		
	}
	

	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public int getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
	}
	public int getReasonCodeLength() {
		return reasonCodeLength;
	}
	public void setReasonCodeLength(int reasonCodeLength) {
		this.reasonCodeLength = reasonCodeLength;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public int getTokenLength() {
		return tokenLength;
	}
	public void setTokenLength(int tokenLength) {
		this.tokenLength = tokenLength;
	}
	public String getPaymentToken() {
		return paymentToken;
	}
	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}
	public int getTokenReferenceIdLength() {
		return tokenReferenceIdLength;
	}
	public void setTokenReferenceIdLength(int tokenReferenceIdLength) {
		this.tokenReferenceIdLength = tokenReferenceIdLength;
	}
	public String getTokenReferenceId() {
		return tokenReferenceId;
	}
	public void setTokenReferenceId(String tokenReferenceId) {
		this.tokenReferenceId = tokenReferenceId;
	}
	public String getTokenExpiry() {
		return tokenExpiry;
	}
	public void setTokenExpiry(String tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}


}
