package com.zak.pro.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zak.pro.enumartion.AttachementType;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Entity
@Table(name = "attachement")
public class Attachement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private AttachementType type;
	private String url;
	private boolean show;
	@JsonIgnore
	private int size;
	@JsonIgnore
	private String public_id;
	@JsonIgnore
	private String format;

	public Attachement() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttachementType getType() {
		return this.type;
	}

	public void setType(AttachementType type) {
		this.type = type;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPublic_id() {
		return this.public_id;
	}

	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isShow() {
		return this.show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}
