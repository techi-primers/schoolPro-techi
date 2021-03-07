package com.zak.pro.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class EncryptionDESUtil {

	public Key setKey(String key) throws UnsupportedEncodingException {
		byte[] keybyte = key.getBytes(("UTF-8"));
		SecretKeySpec secretKey = new SecretKeySpec(keybyte, "DES");
		return secretKey;
	}

	public String encrypt(String strToEncrypt, String secret) throws Exception {
		Key secretKey = this.setKey(secret);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[8]));
		return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	}

	public String decrypt(String strToDecrypt, String secret) throws Exception {
		Key secretKey = this.setKey(secret);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[8]));
		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	}
}
