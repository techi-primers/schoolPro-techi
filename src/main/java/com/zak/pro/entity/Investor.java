package com.zak.pro.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;
import com.zak.pro.enumartion.Category;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Entity(name = "investor")
public class Investor extends Account {

	@Column(name = "first_name")
	@NotNull
	protected String firstName;
	@Column(name = "last_name")
	@NotNull
	protected String lastName;
	private String age;
	private String interests;
	@OneToMany
	private List<Project> favorits;

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public List<Category> getInterests() {
		if ((this.interests != null) && (this.interests.length() > 0)) {
			List<String> names = new ArrayList<String>(Arrays.asList(this.interests.split(";")));
			return names.stream().map(s -> Enum.valueOf(Category.class, s)).collect(Collectors.toList());
		}
		return new ArrayList<Category>();
	}

	public void setInterests(List<Category> interests) {
		if (interests != null) {
			this.interests = String.join(";", interests.stream().map(Category::name).collect(Collectors.toList()));
		}
	}

	public List<Project> getFavorits() {
		return this.favorits;
	}

	public void setFavorits(List<Project> favorits) {
		this.favorits = favorits;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public Investor() {
		super();
		// TODO Auto-generated constructor stub
	}

}
