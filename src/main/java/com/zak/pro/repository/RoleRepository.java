package com.zak.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Role;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);
}
