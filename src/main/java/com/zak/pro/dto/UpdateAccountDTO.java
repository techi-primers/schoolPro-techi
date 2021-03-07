package com.zak.pro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class UpdateAccountDTO {

	private String mobile;
	@JsonProperty(value = "email")
	private String email;
	private String firstName;
	private String lastName;
	private String companyName;
	private String age;
	private String address;
	private String branch;
	private String college;
	private String country;
	private String profilImage;
	private String description;
	private String websiteUrl;
	private boolean allowContact;

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCollege() {
		return this.college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProfilImage() {
		return this.profilImage;
	}

	public void setProfilImage(String profilImage) {
		this.profilImage = profilImage;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsiteUrl() {
		return this.websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public boolean getAllowContact() {
		return this.allowContact;
	}

	public void setAllowContact(boolean allowContact) {
		this.allowContact = allowContact;
	}

	public UpdateAccountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
