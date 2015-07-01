package com.provider.paymenttoken.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import com.provider.paymenttoken.beans.CardInfo;
import com.provider.paymenttoken.beans.TokenInfo;

public class TokenServiceUtil {

	public static boolean validateRequest() {

		return true;
	}

	public static TokenInfo generateToken(CardInfo cardInfo) {
		TokenInfo tokenBean = new TokenInfo();
		SecureRandom random = new SecureRandom();
		tokenBean.setToken(new BigInteger(52, random).toString(10)
				.toUpperCase());
		tokenBean.setExpDate(cardInfo.getExpDate());
		tokenBean.setLast4Digit(cardInfo.getCardNum().substring(
				cardInfo.getCardNum().length() - 4,
				cardInfo.getCardNum().length()));
		return tokenBean;
	}

}
