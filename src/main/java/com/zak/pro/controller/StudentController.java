package com.zak.pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.StudentDTO;
import com.zak.pro.entity.Student;
import com.zak.pro.exception.CustomException;
import com.zak.pro.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author Elkotb Zakaria
 * 
 */
@RestController
@RequestMapping("/invest/me/api/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity registerStudent(@RequestBody StudentDTO studentdto) throws CustomException {
		Student student = this.studentService.registerStudent(studentdto);
		if(student!=null) {
			return new ResponseEntity(student, HttpStatus.OK);
		} else {
			return new ResponseEntity("Issue With Registering Student", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/favoris/investors")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<InvestorDTO> getFavoriteInvestors() {
		return this.studentService.getFavoriteInvestors();
	}

	@GetMapping("/favoris/companies")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<CompanyDTO> getFavoriteComapnies() {
		return this.studentService.getFavoriteCompanies();
	}

	@PostMapping("/favoris/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ResponseEntity bookMarkedInvestor(@PathVariable Long id) throws CustomException {
		this.studentService.bookMarkedInvestorOrCompany(id);
		return new ResponseEntity("Booked Marked",HttpStatus.OK);
	}

	@DeleteMapping("/favoris/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void unbookMarkInvestorOrCompany(@PathVariable Long id) throws Exception {
		this.studentService.unbookMarkInvestorOrCompany(id);
	}

}
