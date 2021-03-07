package com.zak.pro.dto;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class JwtDTO {

	private String jwt;
	private boolean firstTimeConnexion;

	public JwtDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getJwt() {
		return this.jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public boolean isFirstTimeConnexion() {
		return this.firstTimeConnexion;
	}

	public void setFirstTimeConnexion(boolean firstTimeConnexion) {
		this.firstTimeConnexion = firstTimeConnexion;
	}

}
