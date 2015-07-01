/**
 * 
 */
package com.bank.psh.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author user
 *
 */
@JsonInclude(Include.NON_NULL)
public class URNRequestBean {
	
	@NotNull
	//@Size(min=8, max=8, message="URN should be 8 digits in length")
	private String urn;
	
	@NotNull
	private String transactionDetails;

	public String getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
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

}
