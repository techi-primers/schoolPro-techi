package com.zak.pro.service;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.entity.Investor;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
import org.springframework.http.ResponseEntity;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface InvestorService {

	List<Investor> getInvestors();

	Investor registerInvestor(InvestorDTO investordto) throws CustomException;

	void addCategories(List<Category> intrests) throws NoSuchMessageException, CustomException;

	List<ProjectDTO> getFavoritProject();

	void bookmarkProjectForInvestorAndCompany(Long id) throws CustomException;

	void unbookmarkProjectForInvestorAndCompany(Long id) throws CustomException;

    ResponseEntity assignProjectToInvester(Long projectId);

    ResponseEntity getInvesterAssignedProjects();

	ResponseEntity deleteAssignedProjectByInvester(Long projectId);
}
