/**
 * 
 */
package com.bank.psh.mapper;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.bank.psh.beans.CardInfoRequestBean;
import com.bank.psh.data.model.PaymentToken;
import com.bank.psh.data.model.PaymentURN;
import com.provider.token.beans.CardInfo;
import com.provider.token.beans.TokenInfo;

/**
 * @author user
 *
 */
@Component
public class PaymentHubMapper {

	public CardInfo mapCardIfo(CardInfoRequestBean cardDetails) {
		CardInfo info = new CardInfo();
		info.setCardNum(cardDetails.getCardNum());
		info.setExpDate(cardDetails.getExpDate());
		info.setAmount(cardDetails.getAmount());

		return info;
	}

	public PaymentToken mapPaymentToken(TokenInfo token) {
		PaymentToken pToken = new PaymentToken();
		pToken.setExpDate(getExpDate(token.getExpDate()));
		pToken.setCardId("1");
		pToken.setToken(token.getToken());
		pToken.setLast4Digit(token.getLast4Digit());

		return pToken;
	}

	public PaymentURN mapPaymentURN(PaymentToken paymentToken, String urn, String amount) {
		PaymentURN pUrn = new PaymentURN();
		float moneteryValue = 0;
		if (amount!= null && amount!="")
		moneteryValue = Float.parseFloat(amount);
		pUrn.setExpDate(paymentToken.getExpDate());
		pUrn.setUrn(urn);
		pUrn.setMonetaryValue(moneteryValue);
		pUrn.setTokenId(paymentToken.getId());

		return pUrn;
	}
	
	private Date getExpDate(String stDate) {
		Date sqlDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
		try {
			java.util.Date date = formatter.parse(stDate);
			sqlDate = new Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return sqlDate;
	}
}