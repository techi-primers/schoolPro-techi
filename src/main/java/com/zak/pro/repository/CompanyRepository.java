package com.zak.pro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Company;
import com.zak.pro.enumartion.Category;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

	Company findByMobile(String mobile);

	Company findByEmail(String email);

	List<Company> findByInterestsContaining(Category interest);

}
