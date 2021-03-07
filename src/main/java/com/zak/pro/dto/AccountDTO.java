package com.zak.pro.dto;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class AccountDTO {

	String jwt;
	private Long id;

	public String getJwt() {
		return this.jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
