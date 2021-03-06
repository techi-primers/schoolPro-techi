package com.zak.pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.ProjectAddDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.dto.ProjectUpdateDTO;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
import com.zak.pro.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author Elkotb Zakaria
 * 
 */
@RestController
@RequestMapping("/invest/me/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping()
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<ProjectDTO> getProjects(@RequestParam(required = false) List<Category> categories)
			throws NoSuchMessageException, CustomException {
		return this.projectService.getProjects(categories);
	}

	@GetMapping("/students/{id}")
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<ProjectDTO> getProjectsByStudent(@PathVariable Long id) {
		return this.projectService.getProjectsByStudent(id);
	}

	@GetMapping("/students/local")
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<ProjectDTO> getProjectsForConnectedStudent() {
		return this.projectService.getProjectsForConnectedStudent();
	}

	@PostMapping()
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ProjectDTO createProject(@RequestBody ProjectAddDTO projectDto)
			throws NoSuchMessageException, CustomException {
		return this.projectService.createProject(projectDto);
	}

	@PutMapping()
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ProjectDTO updateProject(@RequestBody ProjectUpdateDTO projectDto) throws CustomException {
		return this.projectService.updateProject(projectDto);
	}

	@PostMapping("{id}/attachements")
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void uploadAttachements(@PathVariable long id, @RequestPart MultipartFile[] files,
			@RequestParam(required = false) int show) throws Exception {
		this.projectService.uploadAttachements(id, files, show);
	}

	/*
	 * @PutMapping("attachements/{id}")
	 * 
	 * @PreAuthorize("hasAuthority('STUDENT')")
	 * 
	 * @Operation(security = { @SecurityRequirement(name = "Bearer Token") }) public
	 * Attachement updateAttachement(@PathVariable long id, @RequestPart
	 * MultipartFile file) throws Exception { return
	 * this.projectService.updateAttachement(id, file); }
	 */

	@DeleteMapping("attachements/{id}")
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void deleteAttachement(@PathVariable long id) throws Exception {
		this.projectService.deleteAttachement(id);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void deleteProject(@PathVariable long id) throws Exception {
		this.projectService.deleteProject(id);
	}
}
