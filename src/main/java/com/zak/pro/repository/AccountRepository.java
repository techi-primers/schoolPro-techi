package com.zak.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zak.pro.entity.Account;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByMobile(String mobile);

	Account findByEmail(String email);
}
