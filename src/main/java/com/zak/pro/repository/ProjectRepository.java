package com.zak.pro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Project;
import com.zak.pro.enumartion.Category;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long>  , QuerydslPredicateExecutor {

	List<Project> findByStudent_id(Long id);

	List<Project> findByStudent_email(String email);

	List<Project> findByCategoryIn(List<Category> categories);

	Optional<Project> findById(Long id);
}
