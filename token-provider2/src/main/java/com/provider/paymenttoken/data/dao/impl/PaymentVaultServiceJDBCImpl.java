/**
 * 
 */
package com.provider.paymenttoken.data.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.provider.paymenttoken.beans.CardInfo;
import com.provider.paymenttoken.beans.TokenInfo;
import com.provider.paymenttoken.data.dao.PaymentVaultService;


/**
 * @author user
 */

public class PaymentVaultServiceJDBCImpl implements PaymentVaultService {
	

	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    public boolean storePaymentToken(CardInfo cardInfo, TokenInfo tokenInfo) {
		String query = "INSERT INTO CARD_TOKEN_VAULT (PAN, PAN_EXP_DATE, PAN_LAST4_DIGIT, TOKEN, TOKEN_EXP_DATE) values (?,?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { cardInfo.getCardNum(), cardInfo.getExpDate(), tokenInfo.getLast4Digit(),  tokenInfo.getToken(), tokenInfo.getExpDate() };
	

		int out = jdbcTemplate.update(query, args);

	

		if (out != 0) {
			System.out.println("Payment token saved");
			return true;
		} else {
			System.out.println("Payment token save failed");
			return false;
		}
	}

	



	
}