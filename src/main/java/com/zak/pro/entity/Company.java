package com.zak.pro.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.zak.pro.enumartion.Category;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Entity(name = "company")
public class Company extends Account {

	private String companyName;
	private String interests;
	@OneToMany
	private List<Project> favorits;

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Category> getInterests() {
		if (this.interests != null) {
			List<String> names = new ArrayList<String>(Arrays.asList(this.interests.split(";")));
			return names.stream().map(s -> Enum.valueOf(Category.class, s)).collect(Collectors.toList());
		}
		return new ArrayList<Category>();
	}

	public void setInterests(List<Category> interests) {
		if (interests != null) {
			this.interests = String.join(";", interests.stream().map(Category::name).collect(Collectors.toList()));
		}
		interests = null;
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

}
