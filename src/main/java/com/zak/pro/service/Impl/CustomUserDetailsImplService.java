package com.zak.pro.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zak.pro.entity.Account;
import com.zak.pro.entity.Role;
import com.zak.pro.repository.AccountRepository;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class CustomUserDetailsImplService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String mobileorEmail) throws UsernameNotFoundException {
		Account account = this.accountRepository.findByMobile(mobileorEmail);
		List<Role> roles = new ArrayList<Role>();
		if (account == null) {
			account = this.accountRepository.findByEmail(mobileorEmail);
			if (account == null) {
				throw new UsernameNotFoundException("mobile or email " + mobileorEmail + " not found");
			}
		}
		roles.add(account.getRole());
		List<GrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
				.collect(Collectors.toList());
		return new User(account.getMobile(), account.getPassword(), authorities);
	}

}
