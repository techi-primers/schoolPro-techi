package com.zak.pro.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.istack.NotNull;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class StudentDTO extends AccountDTO {

	private Long id;
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
	private String branch;
	private String college;
	private String country;
	private String profilImage;
	private String public_Id;
	private String description;
	private String websiteUrl;
	private boolean allowContact;
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private String role;

	@Override
	public String toString() {
		StringBuilder student = new StringBuilder("{mobile : ");
		return student.append(this.mobile).append(", password : ").append(this.password).append(", email : ")
				.append(this.email).append(", firstName : ").append(this.firstName).append(", lastName : ")
				.append(this.lastName).append(", age : ").append(this.age).append(", address : ").append(this.address)
				.append(", acceptCGU : ").append(this.acceptCGU).append(", branch : ").append(this.branch)
				.append(", college : ").append(this.college).append(", role : ").append(this.role).append(" }")
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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getPublic_Id() {
		return this.public_Id;
	}

	public void setPublic_Id(String public_Id) {
		this.public_Id = public_Id;
	}

	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
