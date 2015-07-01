package com.bank.psh.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardURNInfoResponseBean {

	private String cardNumber;
	private String urn;
	private String expiryDate;
	private boolean isActive;

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber
	 *            the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the urn
	 */
	public String getUrn() {
		return urn;
	}

	/**
	 * @param urn
	 *            the urn to set
	 */
	public void setUrn(String urn) {
		this.urn = urn;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
			Date exDate = dateFormat.parse(this.expiryDate);
			Date today = new Date(System.currentTimeMillis());
			
			if(today.before(exDate)) {
				this.setActive(true);
			} else {
				this.setActive(false);
			}
		} catch (ParseException ex) {
			this.setActive(false);
		}

	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
