/**
 * 
 */
package com.bank.psh.service.utils;

import java.util.UUID;

import com.bank.psh.data.model.PaymentToken;
import com.bank.psh.service.AuditService;
import com.bank.psh.service.impl.AuditServiceImpl;
import com.bank.psh.service.impl.CryptoGeneratorSrvcImpl;
import com.provider.token.beans.CardInfo;

/**
 * @author user
 *
 */
public class PshUtil {

	/**
	 * 
	 * @return
	 */
	public static String generateURN(CardInfo cardInfo) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String uuidAlpha = uuid.replaceAll("[^a-zA-Z]", "");
		String card4digit = cardInfo.getCardNum().substring(
				cardInfo.getCardNum().length() - 4,
				cardInfo.getCardNum().length());
		String unq = uuidAlpha.toUpperCase();
		unq = unq.substring(unq.length() - 4, unq.length());
		return unq + "" + card4digit;
	}
	
	public static String generateCryptogram(String transactionDetails,
			PaymentToken pmTkn) {
		StringBuilder cryptoBuilder = new StringBuilder();
		cryptoBuilder.append(transactionDetails).append(pmTkn.getToken());
		AuditService srvc = new AuditServiceImpl();
		srvc.logAudit("Initiating transaction unique cryptogram on the value :"+ cryptoBuilder.toString());
		String crypto = new CryptoGeneratorSrvcImpl().generateCryptogram(cryptoBuilder.toString());
		srvc.logAudit("Generated cryptogram :" + crypto);
		return crypto;
	}


	public static void main(String[] args) {
		CardInfo cardInfo = new CardInfo();
		cardInfo.setCardNum("12345");
		System.out.println(generateURN(cardInfo));
	}
}