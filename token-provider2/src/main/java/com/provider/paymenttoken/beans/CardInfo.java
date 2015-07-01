/**
 * 
 */
package com.provider.paymenttoken.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author user
 *
 */
@XmlRootElement
public class CardInfo {
	
	private String cardNum;
	private String expDate;
	
	/**
	 * @return the cardNum
	 */
	public String getCardNum() {
		return cardNum;
	}
	/**
	 * @param cardNum the cardNum to set
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
	 * @param expDate the expDate to set
	 */
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
}