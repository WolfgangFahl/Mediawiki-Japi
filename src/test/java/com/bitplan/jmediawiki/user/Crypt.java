package com.bitplan.jmediawiki.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Crypt {

	private char[] cypher;
	private byte[] salt;

	/**
	 * create me from a password and salt
	 * 
	 * @param pCypher
	 * @param pSalt
	 */
	Crypt(String pCypher, String pSalt) {
		this.cypher = pCypher.toCharArray();
		this.salt = pSalt.getBytes();
	}

	/**
	 * test this
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// regenerate the Crypt parameters with a randomizer before you use this
		Crypt pcf=new Crypt("jr7DlOUFSnsJw6M6nS3ADSFrXf9gK3n5","8XWCfDem");
		String originalPassword = "secretPassword";
		System.out.println("Original password: " + originalPassword);
		String encryptedPassword = pcf.encrypt(originalPassword);
		System.out.println("Encrypted password: " + encryptedPassword);
		String decryptedPassword = pcf.decrypt(encryptedPassword);
		System.out.println("Decrypted password: " + decryptedPassword);
	}

	/**
	 * encrypt the given property
	 * @param property
	 * @return
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 */
	private String encrypt(String property) throws GeneralSecurityException,
			UnsupportedEncodingException {
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(cypher));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(salt, 20));
		return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
	}

	private static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	String decrypt(String property) throws GeneralSecurityException,
			IOException {
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(cypher));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(salt, 20));
		return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	}

	private static byte[] base64Decode(String property) throws IOException {
		return new BASE64Decoder().decodeBuffer(property);
	}

}