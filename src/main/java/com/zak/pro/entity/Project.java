package com.zak.pro.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.zak.pro.converter.StringListConverter;
import com.zak.pro.enumartion.Category;
import com.zak.pro.enumartion.ProjectStatus;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Lob
	@Column(name = "description", length = 3000)
	@Type(type = "org.hibernate.type.TextType")
	private String description;
	@Enumerated(EnumType.STRING)
	private ProjectStatus status;
	@Enumerated(EnumType.STRING)
	private Category category;
	private String videoUrl;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attachement> attachements;
	@ManyToOne
	private Student student;
	@Convert(converter = StringListConverter.class)
	private List<String> groupMember;

	public Project() {
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

	public List<Attachement> getAttachements() {
		return this.attachements;
	}

	public void setAttachements(List<Attachement> attachements) {
		this.attachements = attachements;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
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
