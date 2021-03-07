package com.zak.pro.util;

import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Component
public class HashingUtil {

	public String calculateHashInMD5(String value) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(value.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(md.digest());
	}
}
