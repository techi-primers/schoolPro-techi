package com.zak.pro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Project;
import com.zak.pro.enumartion.Category;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByStudent_id(Long id);

	List<Project> findByStudent_email(String email);

	List<Project> findByCategoryIn(List<Category> categories);
}
