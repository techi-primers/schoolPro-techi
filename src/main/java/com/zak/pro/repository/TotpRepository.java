package com.zak.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Totp;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface TotpRepository extends JpaRepository<Totp, Long> {

}
