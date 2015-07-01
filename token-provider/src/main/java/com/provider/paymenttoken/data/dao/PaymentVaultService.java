/**
 * 
 */
package com.provider.paymenttoken.data.dao;

import com.provider.paymenttoken.beans.CardInfo;
import com.provider.paymenttoken.beans.TokenInfo;

/**
 * @author user
 *
 */
public interface PaymentVaultService {

	public boolean storePaymentToken(CardInfo cardInfo, TokenInfo tokenInfo);


}
