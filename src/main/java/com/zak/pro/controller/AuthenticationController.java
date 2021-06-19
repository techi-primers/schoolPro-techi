package com.zak.pro.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zak.pro.dto.AuthenticationDTO;
import com.zak.pro.dto.ForgetPasswordResetDTO;
import com.zak.pro.dto.JwtDTO;
import com.zak.pro.dto.TotpDTO;
import com.zak.pro.exception.CustomException;
import com.zak.pro.service.AuthenticationService;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@RestController
@RequestMapping("/invest/me/api")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticatonService;

	@PostMapping("/authentication")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity authenticate(@RequestBody AuthenticationDTO authenticationDTO)
			throws AuthenticationException, IOException {
		JwtDTO jwtDTO = this.authenticatonService.authenticateUser(authenticationDTO);
		if(jwtDTO!=null) {
			return new ResponseEntity(jwtDTO,HttpStatus.OK);
		} else {
			return new ResponseEntity("issue with authentication",HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/totp")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity saveTotp(@RequestBody TotpDTO totpdto) throws Exception {
		this.authenticatonService.saveTotp(totpdto.getEmail());
		return new ResponseEntity("saved totp",HttpStatus.OK);
	}

	@PostMapping("/reset")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity forgetPassword(@RequestBody ForgetPasswordResetDTO passwordResetDTO) throws CustomException {
		this.authenticatonService.resetPassword(passwordResetDTO);
		return new ResponseEntity("reset password done",HttpStatus.OK);
	}
}
