package com.zak.pro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Investor;
import com.zak.pro.enumartion.Category;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface InvestorRepository extends JpaRepository<Investor, Long> {

	Investor findByMobile(String mobile);

	Investor findByEmail(String email);

	List<Investor> findByInterestsContaining(Category interest);
}
