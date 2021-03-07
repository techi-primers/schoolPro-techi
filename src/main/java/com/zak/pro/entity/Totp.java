package com.zak.pro.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "totp")
public class Totp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String totp;
	private LocalDateTime validity;

	public Totp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTotp() {
		return this.totp;
	}

	public void setTotp(String totp) {
		this.totp = totp;
	}

	public LocalDateTime getValidity() {
		return this.validity;
	}

	public void setValidity(LocalDateTime validity) {
		this.validity = validity;
	}

}
