/**
 * 
 */
package com.provider.token.service;

import com.provider.token.beans.CardInfo;
import com.provider.token.beans.TokenInfo;

/**
 * @author user
 *
 */
public interface PaymentTokenServiceProvider {
	
	TokenInfo generateToken(CardInfo cardInfo);

}
