package com.provider.paymenttoken.service.impl;

import java.util.Iterator;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.provider.paymenttoken.beans.CardInfo;
import com.provider.paymenttoken.beans.PaymentTokenRequestBean;
import com.provider.paymenttoken.beans.PaymentTokenResponseBean;
import com.provider.paymenttoken.beans.TokenInfo;
import com.provider.paymenttoken.data.dao.PaymentVaultService;
import com.provider.paymenttoken.service.PaymentTokenService;
import com.provider.paymenttoken.service.TokenServiceUtil;

@RestController
public class PaymentTokenServiceImpl {

	public PaymentVaultService paymentVaultService;

	@Autowired
	public void setPaymentVaultService(PaymentVaultService paymentVaultService) {
		this.paymentVaultService = paymentVaultService;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PaymentTokenServiceImpl.class);

	@RequestMapping(value = "/payment/token", method = RequestMethod.POST)
	public @ResponseBody PaymentTokenResponseBean retrievePaymentToken(
			@Valid @RequestBody PaymentTokenRequestBean requestBean) {
		
		/*if(result.hasErrors()) {
			System.out.println("ERRRRRRRRRRRRRRRR");
			
			for (ObjectError err : result.getAllErrors()) {
				System.out.println(err.getDefaultMessage());
			}
			
		}*/
		
		PaymentTokenResponseBean respBean = new PaymentTokenResponseBean();
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNum(requestBean.getPanNo());
		cardInfo.setExpDate(requestBean.getPanExpiryDate());
		TokenInfo tokenInfo = TokenServiceUtil.generateToken(cardInfo);
		boolean tokenStored = paymentVaultService.storePaymentToken(cardInfo,
				tokenInfo);
		if (tokenStored) {
			respBean.setPaymentToken(tokenInfo.getToken());
			respBean.setTokenExpiry(tokenInfo.getExpDate());
		} else {
			respBean.setReasonCode("Token generation failed!!!");
		}

		return respBean;
	}

}
