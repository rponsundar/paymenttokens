/**
 * 
 */
package com.provider.token.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.provider.token.beans.CardInfo;
import com.provider.token.beans.TokenInfo;
import com.provider.token.service.PaymentTokenServiceProvider;

/**
 * @author user
 *
 */

public class PaymentTokenServiceProviderImpl implements
		PaymentTokenServiceProvider {

	@Override
	public TokenInfo generateToken(CardInfo cardInfo) {
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

	public static void main(String[] args) {
		PaymentTokenServiceProviderImpl impl = new PaymentTokenServiceProviderImpl();
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNum("12345678912345678");
		cardInfo.setExpDate("12/12/2018");
		System.out.println("Token generated... "
				+ impl.generateToken(cardInfo).getToken());
	}
}
