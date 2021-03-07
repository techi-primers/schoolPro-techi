package com.zak.pro.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zak.pro.entity.CGU;
import com.zak.pro.repository.CGURepository;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@RestController
@RequestMapping("/invest/me/api/contents")
public class CGUController {

	@Autowired
	private CGURepository cguRepository;

	@GetMapping("/cgu")
	@ResponseStatus(HttpStatus.OK)
	public Optional<CGU> showCGU() {
		return this.cguRepository.findById(1L);
	}
}
