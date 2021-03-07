package com.zak.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.EmailBeforRefresh;

public interface EmailBeforeRefreshRepository extends JpaRepository<EmailBeforRefresh, Long> {

	EmailBeforRefresh findByEmail(String email);
}
