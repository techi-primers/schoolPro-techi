package com.zak.pro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
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

import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.entity.Investor;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
import com.zak.pro.service.InvestorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author Elkotb Zakaria
 * 
 */
@RestController
@RequestMapping("/invest/me/api/investors")
public class InvestorController {

	@Autowired
	private InvestorService investorService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity registerInvestor(@RequestBody InvestorDTO investordto) throws CustomException {
		Investor investor = this.investorService.registerInvestor(investordto);
		if(investor!=null) {
			return new ResponseEntity(investor,HttpStatus.OK);
		} else {
			return new ResponseEntity("issue with registering investor",HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('STUDENT')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<Investor> getInvestors() {
		return this.investorService.getInvestors();
	}

	@PostMapping("/categories")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ResponseEntity addCategories(@RequestBody List<Category> intrests) throws NoSuchMessageException, CustomException {
		this.investorService.addCategories(intrests);
		return new ResponseEntity("added categories",HttpStatus.OK);
	}

	@GetMapping("/favoris")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<ProjectDTO> getFavoris() {
		return this.investorService.getFavoritProject();
	}

	@PostMapping("/favoris/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ResponseEntity bookMarkedProject(@PathVariable Long id) throws NoSuchMessageException, CustomException {
		this.investorService.bookmarkProjectForInvestorAndCompany(id);
		return new ResponseEntity("book marked project",HttpStatus.OK);
	}

	@DeleteMapping("/favoris/{id}")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void unbookMarkedProject(@PathVariable Long id) throws NoSuchMessageException, CustomException {
		this.investorService.unbookmarkProjectForInvestorAndCompany(id);
	}

	@PostMapping("/assignproject/{projectId}")
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ResponseEntity assignProjectToInvester(@PathVariable Long projectId)  {
		return this.investorService.assignProjectToInvester(projectId);
	}

	@PostMapping("/deleteAssignedProjectByInvester/{projectId}")
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ResponseEntity deleteAssignedProjectByInvester(@PathVariable Long projectId)  {
		return this.investorService.deleteAssignedProjectByInvester(projectId);
	}

	@GetMapping("/getInvesterAssignedProjects")
	@PreAuthorize("hasAuthority('INVESTOR')")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public ResponseEntity getInvesterAssignedProjects()  {
		return this.investorService.getInvesterAssignedProjects();
	}


}
