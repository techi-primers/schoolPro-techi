package com.zak.pro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class ForgetPasswordResetDTO {

	private String email;
	private String password;
	@Schema(description = "totp should not be sent encrypted")
	private String totp;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTotp() {
		return this.totp;
	}

	public void setTotp(String totp) {
		this.totp = totp;
	}

	public ForgetPasswordResetDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
