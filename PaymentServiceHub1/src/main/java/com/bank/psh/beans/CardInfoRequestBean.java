/**
 * 
 */
package com.bank.psh.beans;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author user
 *
 */
@JsonInclude(Include.NON_NULL)
public class CardInfoRequestBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	@NotNull
	@Size(min=16, max=16, message="Card number should be 16 digits in length")
	private String cardNum;
	
	@NotNull(message="PAN Expiry Date can not be null")
	@Size(min=6, max=9, message="Expiry Date is invalid")
	private String expDate;
	
	private String cvv;
	
	private String amount;
	

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}

	/**
	 * @param cardNum
	 *            the cardNum to set
	 */
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
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

	/**
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * @param cvv
	 *            the cvv to set
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	@Override
	public String toString() {
		return  "Name: " + name + " Card: " + cardNum;
	}

}
