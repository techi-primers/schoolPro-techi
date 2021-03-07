package com.zak.pro.dto;

import java.util.List;

import com.zak.pro.entity.Attachement;
import com.zak.pro.enumartion.Category;
import com.zak.pro.enumartion.ProjectStatus;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class ProjectDTO {

	private Long id;
	private String name;
	private String description;
	private ProjectStatus status;
	private Category category;
	private String videoUrl;
	private List<Attachement> attachements;
	private StudentDTO student;
	private Long studentId;
	private List<String> groupMember;

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

	public List<Attachement> getAttachements() {
		return this.attachements;
	}

	public void setAttachements(List<Attachement> attachements) {
		this.attachements = attachements;
	}

	public StudentDTO getStudent() {
		return this.student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public ProjectDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<String> getGroupMember() {
		return this.groupMember;
	}

	public void setGroupMember(List<String> groupMember) {
		this.groupMember = groupMember;
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
