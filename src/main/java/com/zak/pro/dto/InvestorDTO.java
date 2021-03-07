package com.zak.pro.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.istack.NotNull;
import com.zak.pro.enumartion.Category;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class InvestorDTO extends AccountDTO {

	@NotNull
	@Size(min = 10, max = 20)
	private String mobile;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Size(min = 8)
	@NotNull
	private String password;
	@Email
	@NotNull
	private String email;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	private String age;
	private String address;
	private boolean acceptCGU;
	private String country;
	private String profilImage;
	private String publicId;
	private String description;
	private boolean allowContact;
	@NotNull
	private String role;
	private List<Category> interests;
	private String websiteUrl;

	@Override
	public String toString() {
		StringBuilder student = new StringBuilder("{mobile : ");
		return student.append(this.mobile).append(", password : ").append(this.password).append(", email : ")
				.append(this.email).append(", firstName : ").append(this.firstName).append(", lastName : ")
				.append(this.lastName).append(", age : ").append(this.age).append(", address : ").append(this.address)
				.append(", acceptCGU : ").append(this.acceptCGU).append(", role : ").append(this.role).append(" }")
				.toString();
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isAcceptCGU() {
		return this.acceptCGU;
	}

	public void setAcceptCGU(boolean acceptCGU) {
		this.acceptCGU = acceptCGU;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Category> getInterests() {
		return this.interests;
	}

	public void setInterests(List<Category> interests) {
		this.interests = interests;
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

	public boolean isAllowContact() {
		return this.allowContact;
	}

	public void setAllowContact(boolean allowContact) {
		this.allowContact = allowContact;
	}

	public String getPublicId() {
		return this.publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

}
