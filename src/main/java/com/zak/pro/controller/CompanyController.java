package com.zak.pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.entity.Company;
import com.zak.pro.exception.CustomException;
import com.zak.pro.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author Elkotb Zakaria
 * 
 */
@RestController
@RequestMapping("/invest/me/api/companies")
public class CompanyController {
//
	@Autowired
	private CompanyService companyService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Company registerCompany(@RequestBody CompanyDTO companydto) throws CustomException {
		return this.companyService.registerCompany(companydto);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<Company> getInvestors() {
		return this.companyService.getCompany();
	}
}
