package com.zak.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Student;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByMobile(String mobile);

	Student findByEmail(String email);
}
