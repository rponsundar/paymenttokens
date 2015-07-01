package com.provider.paymenttoken.service;

import com.provider.paymenttoken.beans.PaymentTokenRequestBean;
import com.provider.paymenttoken.beans.PaymentTokenResponseBean;

public interface PaymentTokenService {
	
	PaymentTokenResponseBean retrievePaymentToken (PaymentTokenRequestBean tokenRequest);

}
