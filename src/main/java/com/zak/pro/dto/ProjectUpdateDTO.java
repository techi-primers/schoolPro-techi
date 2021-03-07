package com.zak.pro.dto;

import java.util.List;

import com.zak.pro.enumartion.Category;
import com.zak.pro.enumartion.ProjectStatus;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class ProjectUpdateDTO {

	private Long id;
	private String name;
	private String description;
	private ProjectStatus status;
	private Category category;
	private String videoUrl;
	private List<String> groupMember;

	public ProjectUpdateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public List<String> getGroupMember() {
		return this.groupMember;
	}

	public void setGroupMember(List<String> groupMember) {
		this.groupMember = groupMember;
	}

}
