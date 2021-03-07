package com.zak.pro.service;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;

import com.zak.pro.dto.AuthenticationDTO;
import com.zak.pro.dto.ForgetPasswordResetDTO;
import com.zak.pro.dto.JwtDTO;
import com.zak.pro.exception.CustomException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface AuthenticationService {

	JwtDTO authenticateUser(AuthenticationDTO authenticationDTO) throws AuthenticationException, IOException;

	void saveTotp(String email) throws Exception;

	void resetPassword(ForgetPasswordResetDTO passwordResetDTO) throws CustomException;

	String updateToken(String email) throws AuthenticationException, IOException;
}
