package com.provider.token.service.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bank.psh.beans.PaymentTokenRequestBean;
import com.bank.psh.beans.PaymentTokenResponseBean;
import com.provider.token.beans.CardInfo;
import com.provider.token.beans.TokenInfo;
import com.provider.token.service.PaymentTokenServiceProvider;

@Component
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ExternalPaymentTokenSrvcProviderImpl implements
PaymentTokenServiceProvider {
	
	private static final String PROP_FILE_NAME = "application.properties";

	@Override
	public TokenInfo generateToken(CardInfo cardInfo) {
		System.out.println("external provider....");
		TokenInfo tokenInfo = new TokenInfo() ;
		try {
		RestTemplate templ = new RestTemplate();
		templ.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		templ.getMessageConverters().add(new StringHttpMessageConverter());
		PaymentTokenRequestBean req = new PaymentTokenRequestBean();
		req.setPanNo(cardInfo.getCardNum());
		req.setPanExpiryDate(cardInfo.getExpDate());
		req.setTokenRequestorId(1010);
		ResponseEntity<PaymentTokenResponseBean> resp = templ.postForEntity(getEndPointURL("tokenprovider.endpointURL"), req, PaymentTokenResponseBean.class);
		PaymentTokenResponseBean respBean = resp.getBody();
		tokenInfo.setExpDate(respBean.getTokenExpiry());
		tokenInfo.setToken(respBean.getPaymentToken());
		tokenInfo.setLast4Digit(cardInfo.getCardNum().substring(
				cardInfo.getCardNum().length() - 4,
				cardInfo.getCardNum().length()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenInfo;
	}
	
	/**
	 * getEndPointURL
	 * 
	 * @param widgetId
	 * @return
	 * @throws Exception
	 */
	private String getEndPointURL(String tokenProviderURL) throws Exception {

		String endpointURL = null;
		Properties props = new Properties();

		InputStream inStream = ExternalPaymentTokenSrvcProviderImpl.class.getClassLoader()
				.getResourceAsStream(PROP_FILE_NAME);
		if (null != inStream) {
			props.load(inStream);
			endpointURL = props.getProperty(tokenProviderURL);
		} else {
			throw new FileNotFoundException("Property file " + PROP_FILE_NAME
					+ "not found in classpath");
		}

		if (null == endpointURL) {
			throw new Exception("Unable to read Token Service Provider Endpoint URL "
					+ endpointURL + " in " + PROP_FILE_NAME);
		}

		return endpointURL;
	}

}
