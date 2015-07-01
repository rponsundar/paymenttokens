/**
 * 
 */
package com.bank.psh.data.dao.impl;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bank.psh.beans.CardURNInfoResponseBean;
import com.bank.psh.beans.ClientRegistrationRequest;
import com.bank.psh.beans.UserBean;
import com.bank.psh.controller.model.ClientDetails;
import com.bank.psh.data.dao.PaymentServiceHubDao;
import com.bank.psh.data.model.PaymentToken;
import com.bank.psh.data.model.PaymentURN;

/**
 * @author user
 */
@Component
@Transactional
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PaymentServiceHubDaoImpl implements PaymentServiceHubDao {

	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	@Transactional
	public void savePaymentToken(PaymentToken paymentToken) {
		String query = "INSERT INTO CUST_PYMT_TOKEN (TOKEN, EXP_DATE, CARD_ID, PAN_LAST4_DIGIT) values (?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { paymentToken.getToken(),
				paymentToken.getExpDate(), paymentToken.getCardId(),
				paymentToken.getLast4Digit() };

		int out = jdbcTemplate.update(query, args);

		// TODO below code needs to be tuned properly
		query = "SELECT max(id) id from CUST_PYMT_TOKEN";

		long id = jdbcTemplate.queryForLong(query);
		paymentToken.setId((int) id);

		if (out != 0) {
			System.out.println("Payment token saved with id="
					+ paymentToken.getId());
		} else {
			System.out.println("Payment token save failed with id="
					+ paymentToken.getId());
		}
	}

	@Override
	public void savePaymentURN(PaymentURN paymentURN) {
		String query = "INSERT INTO CUST_URN_TOKEN (URN, TOKEN_ID, EXP_DATE, MONETARY_VALUE) values (?,?,?,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { paymentURN.getUrn(),
				paymentURN.getTokenId(), paymentURN.getExpDate(),
				paymentURN.getMonetaryValue() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Payment token saved with id="
					+ paymentURN.getId());
		} else {
			System.out.println("Payment token save failed with id="
					+ paymentURN.getId());
		}
	}

	@Override
	public PaymentToken getToken(String urn) {
		String query = "SELECT TOKEN.TOKEN TOKEN, TOKEN.EXP_DATE EXP_DATE FROM CUST_URN_TOKEN URN, CUST_PYMT_TOKEN TOKEN WHERE URN.URN = ? AND URN.TOKEN_ID = TOKEN.ID";
		PaymentToken paymentToken = null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { urn };

		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, args);

		if (srs != null) {
			paymentToken = new PaymentToken();
			while (srs.next()) {
				paymentToken.setToken(srs.getString("TOKEN"));
				// paymentToken.setExpDate(srs.getString("EXP_DATE"));
			}
		}

		return paymentToken;
	}

	@Override
	public ClientDetails registerClient(
			ClientDetails clientDetails) {
		String query = "INSERT INTO oauth_client_details(client_id, client_name, client_domain, resource_ids, scope, authorized_grant_types,  web_server_redirect_uri, authorities, "
				+ "access_token_validity, autoapprove) VALUES (?,?,?,?,?,?,?,?,?,?)" ;
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] args = new Object[] { clientDetails.getClientId(),
				clientDetails.getClientName(), clientDetails.getDomain(), clientDetails.getResourceId(), 
				clientDetails.getScope(), clientDetails.getAuthorization_grants(), clientDetails.getRedirectURI(),
				clientDetails.getAuthorities(), clientDetails.getAccessValidity(), clientDetails.getAutoApproval()};

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Client Registration successful with id="
					+ clientDetails.getClientId());
		} else {
			System.out.println("Client Registration failed");
			clientDetails.setClientId(null);
		}
		
		return clientDetails;
	}

	@Override
	public boolean validateUser(UserBean user) {
		boolean userfound = false;
		String query = "SELECT ID FROM CUST WHERE LOGIN_ID = ? AND PASSWORD = ?";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] { user.getUserId(), user.getPassword() };
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, args);
		if (srs != null) {
			while (srs.next()) {
				userfound = true;
			}
		}
		return userfound;
	}

	@Override
	public ArrayList<String> getCustCards(String custId) {
		ArrayList<String> custCards = new ArrayList<String>();
		String query = "SELECT CC.PAN_NUM PAN_NUM FROM CUST_CARD CC, CUST C WHERE C.LOGIN_ID = ? AND CC.CUST_ID = C.ID";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] { custId };
		System.out.println("custId.."+custId);
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, args);
		if (srs != null) {
			while (srs.next()) {
				custCards.add(srs.getString("PAN_NUM"));
			}
		}
		return custCards;
	}
	


	@Override
	public String expireTokenURN(String token) {
		System.out.println("token:"+token);
		String URNQuery = "UPDATE CUST_URN_TOKEN SET EXPIRED = 'Y' WHERE TOKEN_ID = (SELECT ID FROM CUST_PYMT_TOKEN WHERE TOKEN = ?)";
		String tokenQuery = "UPDATE CUST_PYMT_TOKEN SET EXPIRED = 'Y' WHERE TOKEN = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] {token };
		int urnUpdate  = jdbcTemplate.update(URNQuery, args);
		int tokenUpdate = jdbcTemplate.update(tokenQuery, args);
        if (urnUpdate == 1 && tokenUpdate == 1) return "Token successfully expired";
		return "Token expiry failed";
	}

	@Override
	public ArrayList<CardURNInfoResponseBean> getCustCardURNInfo(String custId) {
		ArrayList<CardURNInfoResponseBean> custCardTokenInfo = new ArrayList<CardURNInfoResponseBean>();
		String query = "select c.PAN PAN, c.token_exp_date exp_date, d.URN urn from cust a inner join cust_card b on b.cust_id = a.id "
				+ "inner join card_token_vault c on c.PAN = b.PAN_NUM inner join cust_urn_token d on  d.token_id = c.id "
				+ "inner join users e on e.username = a.login_id where e.username = ? order by c.PAN;";
	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[] { custId };
		System.out.println("custId.."+custId);
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, args);
		if (srs != null) {
			CardURNInfoResponseBean bean = null;
			while (srs.next()) {
				bean = new CardURNInfoResponseBean();
				bean.setCardNumber(srs.getString("PAN"));
				bean.setExpiryDate(srs.getString("exp_date"));
				bean.setUrn(srs.getString("urn"));
				custCardTokenInfo.add(bean);
			}
		}
		return custCardTokenInfo;
	}
}