package com.zak.pro.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.istack.NotNull;

/**
 *
 * @author Elkotb Zakaria
 *
 */

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 20, nullable = false, unique = true)
	@Size(min = 10, max = 20)
	@NotNull
	protected String mobile;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false)
	@Size(min = 8)
	protected String password;
	@Column(length = 50, nullable = false, unique = true)
	@Email
	@NotNull
	protected String email;
	protected String address;
	protected String country;
	protected String profilImage;
	@JsonIgnore
	protected String public_id;
	protected String description;
	protected String websiteUrl;
	protected boolean allowContact;
	@Column(name = "accept_cgu")
	protected boolean acceptCGU;
	@Column(name = "first_time")
	protected boolean firstTime;
	@Column(name = "nbr_connection")
	protected int nbrConnection;
	@ManyToOne
	@JoinColumn(name = "role")
	@JsonProperty(access = Access.WRITE_ONLY)
	protected Role role;
	@JsonIgnore
	protected String pushRegistrationId;
	@OneToOne
	@JsonIgnore
	private Totp totp;

	@Override
	public String toString() {
		StringBuilder student = new StringBuilder("{mobile : ");
		return student.append(this.mobile).append(", password : ").append(this.password).append(", email : ")
				.append(this.email).append(", address : ").append(this.address).append(", acceptCGU : ")
				.append(this.acceptCGU).append(", role : ").append(this.role.toString()).append(" }").toString();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public Totp getTotp() {
		return this.totp;
	}

	public void setTotp(Totp totp) {
		this.totp = totp;
	}

	public String getPushRegistrationId() {
		return this.pushRegistrationId;
	}

	public void setPushRegistrationId(String pushRegistrationId) {
		this.pushRegistrationId = pushRegistrationId;
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

	public String getPublic_id() {
		return this.public_id;
	}

	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

	public boolean isFirstTime() {
		return this.firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public int getNbrConnection() {
		return this.nbrConnection;
	}

	public void setNbrConnection(int nbrConnection) {
		this.nbrConnection = nbrConnection;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

}
