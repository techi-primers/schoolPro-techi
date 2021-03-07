package com.zak.pro.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public JwtDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO)
			throws AuthenticationException, IOException {
		return this.authenticatonService.authenticateUser(authenticationDTO);
	}

	@PostMapping("/totp")
	@ResponseStatus(HttpStatus.OK)
	public void saveTotp(@RequestBody TotpDTO totpdto) throws Exception {
		this.authenticatonService.saveTotp(totpdto.getEmail());
	}

	@PostMapping("/reset")
	@ResponseStatus(HttpStatus.OK)
	public void forgetPassword(@RequestBody ForgetPasswordResetDTO passwordResetDTO) throws CustomException {
		this.authenticatonService.resetPassword(passwordResetDTO);
	}
}
