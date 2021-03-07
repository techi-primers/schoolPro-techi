package com.zak.pro.service;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.entity.Company;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface CompanyService {

	Company registerCompany(CompanyDTO company) throws CustomException;

	List<Company> getCompany();

	void addCategories(List<Category> intrests) throws NoSuchMessageException, CustomException;

}
