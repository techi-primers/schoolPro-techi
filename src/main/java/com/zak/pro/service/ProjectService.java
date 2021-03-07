package com.zak.pro.service;

import java.io.IOException;
import java.util.List;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.ProjectAddDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.dto.ProjectUpdateDTO;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface ProjectService {

	List<ProjectDTO> getProjects(List<Category> categories) throws NoSuchMessageException, CustomException;

	List<ProjectDTO> getProjectsByStudent(Long id);

	List<ProjectDTO> getProjectsForConnectedStudent();

	void uploadAttachements(Long id, MultipartFile[] file, int show) throws Exception;

	// Attachement updateAttachement(Long id, MultipartFile file) throws
	// CustomException, IOException;

	boolean deleteAttachement(Long id) throws CustomException, IOException;

	ProjectDTO createProject(ProjectAddDTO projectDto) throws NoSuchMessageException, CustomException;

	ProjectDTO updateProject(ProjectUpdateDTO projectDto) throws CustomException;

	void deleteProject(Long id) throws CustomException, IOException;

}
