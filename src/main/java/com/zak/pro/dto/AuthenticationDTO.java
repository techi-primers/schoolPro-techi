package com.zak.pro.dto;

import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class AuthenticationDTO {

	@NotNull
	@Size(min = 10, max = 50)
	private String mobileOrEmail;
	@NotNull
	@Size(min = 8)
	private String password;

	@Override
	public String toString() {
		StringBuilder audit = new StringBuilder("{mobileOrEmail : ");
		return audit.append(this.mobileOrEmail).toString();
	}

	public String getMobileOrEmail() {
		return this.mobileOrEmail;
	}

	public void setMobileOrEmail(String mobileOrEmail) {
		this.mobileOrEmail = mobileOrEmail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
