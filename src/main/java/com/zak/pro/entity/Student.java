package com.zak.pro.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Entity(name = "student")
public class Student extends Account {

	@Column(name = "first_name")
	@NotNull
	protected String firstName;
	@Column(name = "last_name")
	@NotNull
	protected String lastName;
	private String branch;
	private String College;
	private String age;
	@OneToMany
	private List<Investor> favoriteInvestors;
	@OneToMany
	private List<Company> favoriteCompanies;

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

	public String getBranch() {
		return this.branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCollege() {
		return this.College;
	}

	public void setCollege(String college) {
		this.College = college;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public List<Investor> getFavoriteInvestors() {
		return this.favoriteInvestors;
	}

	public void setFavoriteInvestors(List<Investor> favoriteInvestors) {
		this.favoriteInvestors = favoriteInvestors;
	}

	public List<Company> getFavoriteCompanies() {
		return this.favoriteCompanies;
	}

	public void setFavoriteCompanies(List<Company> favoriteCompanies) {
		this.favoriteCompanies = favoriteCompanies;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		StringBuilder student = new StringBuilder("{mobile : ");
		return student.append(this.mobile).append(", password : ").append(this.password).append(", email : ")
				.append(this.email).append(", firstName : ").append(this.firstName).append(", lastName : ")
				.append(this.lastName).append(", age : ").append(this.age).append(", address : ").append(this.address)
				.append(", acceptCGU : ").append(this.acceptCGU).append(", branch : ").append(this.branch)
				.append(", college : ").append(this.College).append(this.role.toString()).append(" }").toString();
	}
}
