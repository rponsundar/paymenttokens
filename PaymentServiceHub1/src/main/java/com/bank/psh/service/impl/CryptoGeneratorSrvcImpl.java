package com.bank.psh.service.impl;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.bank.psh.service.CryptoGeneratorSrvc;

public class CryptoGeneratorSrvcImpl implements CryptoGeneratorSrvc {
	
	static Cipher cipher;
	private static byte[] encryptedBytes = null;	
	
	@Override
	public String generateCryptogram(String message) {
		
		Path path = Paths.get("D:/PSH/KeyStore.txt");
		byte[] symmKey = null;
		String encryptedMsg = null;
		try {
			symmKey = Files.readAllBytes(path);
			SecretKey originalKey = new SecretKeySpec(symmKey, 0, symmKey.length, "AES");
			encryptedMsg = encrypt(message, originalKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedMsg;
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		// logic for key generation
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
	    FileOutputStream stream = new FileOutputStream("D:/PSH/KeyStore.txt");
		  try {
		      stream.write(secretKey.getEncoded());
		  } catch (Exception e){
			  e.printStackTrace(); 
		  }
		  finally {
		      stream.close();
		  }
		  
		 /* logic for decryption from encrypted key stored in a file
		  try {
			  Path path = Paths.get("D:/PSH/KeyStore.txt");
				byte [] symmKey = Files.readAllBytes(path);
				SecretKey originalKey = new SecretKeySpec(symmKey, 0, symmKey.length, "AES");
				String encryptedMsg = encrypt("sometexttoencrypt", originalKey);
				System.out.println(decrypt(encryptedMsg, originalKey));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        */
	}
	
	
    /**
     * Encryption using AES
     * 
     */
	public static String encrypt(String plainText, SecretKey secretKey)
			throws Exception {
		byte[] plainTextByte = plainText.getBytes();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		encryptedBytes = encryptedByte;
		//System.out.println(encryptedByte);
		return encryptedByte.toString();
	}
    /**
     * Decryption using AES
     */
	public static String decrypt(String encryptedText, SecretKey secretKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedBytes);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
}