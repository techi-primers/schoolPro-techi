package com.zak.pro.util;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Component
public class PasswordUtil {

	public boolean isPasswordValid(String password) {
		if (this.isMoreThan8(password) && this.hasSpace(password) && this.hasUpperLowerCase(password)
				&& this.hasNumber(password) && this.hasSpecialChar(password)) {
			return true;
		}
		return false;
	}

	private boolean isMoreThan8(String password) {
		if (password.length() > 8) {
			return true;
		}
		return false;
	}

	private boolean hasUpperLowerCase(String password) {
		return (!password.equals(password.toUpperCase()) && !password.equals(password.toLowerCase()));
	}

	private boolean hasSpecialChar(String password) {
		return !password.matches("[A-Za-z0-9]*");
	}

	private boolean hasNumber(String password) {
		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private boolean hasSpace(String password) {
		return password.equals(password.trim());
	}
}
