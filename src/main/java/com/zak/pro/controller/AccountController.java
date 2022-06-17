package com.zak.pro.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.AccountDTO;
import com.zak.pro.dto.UpdateAccountDTO;
import com.zak.pro.dto.UploadImageDTO;
import com.zak.pro.exception.CustomException;
import com.zak.pro.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author Elkotb Zakaria
 * 
 */
@RestController
@RequestMapping("/invest/me/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public List<String> showAccounts() {
		List<String> accounts = new ArrayList<String>();
		accounts.add("accounts");
		return accounts;
	}

	@PutMapping("/push/{pushRegistrationId}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void updatePushRegistrationId(@PathVariable String pushRegistrationId)
			throws NoSuchMessageException, CustomException {
		this.accountService.updatePushRegistrationId(pushRegistrationId);
	}

	@PostMapping("/pdp")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity uploadProfilImage(@RequestPart MultipartFile file) throws CustomException, IOException {
		UploadImageDTO uploadImageDTO = this.accountService.uploadProfilImage(file);
		if(uploadImageDTO!=null) {
			return new ResponseEntity(uploadImageDTO,HttpStatus.OK);
		} else {
			return new ResponseEntity("issue with upload profile image",HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/pdp/image")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void updateProfilImage(@RequestPart MultipartFile file) throws CustomException, IOException {
		this.accountService.updateProfilImage(file);
	}

	@PutMapping("/profil")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public AccountDTO updateProfil(@RequestBody UpdateAccountDTO accountDTO) throws Exception {
		return this.accountService.updateProfil(accountDTO);
	}

	@GetMapping("/profil")
	@ResponseStatus(HttpStatus.OK)
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public AccountDTO getProfil() throws CustomException, IOException {
		return this.accountService.getProfilDetail();
	}

	@DeleteMapping("/profil")
	@ResponseStatus(HttpStatus.OK)
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public void deleteProfil() throws CustomException, IOException {
		this.accountService.deleteProfile();
	}

}
