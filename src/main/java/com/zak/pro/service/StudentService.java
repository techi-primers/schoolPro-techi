package com.zak.pro.service;

import java.util.List;

import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.StudentDTO;
import com.zak.pro.entity.Student;
import com.zak.pro.exception.CustomException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface StudentService {

	Student registerStudent(StudentDTO student) throws CustomException;

	void bookMarkedInvestorOrCompany(Long id) throws CustomException;

	List<CompanyDTO> getFavoriteCompanies();

	List<InvestorDTO> getFavoriteInvestors();

	void unbookMarkInvestorOrCompany(Long id) throws Exception;

}
