package com.zak.pro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @author Techi Primers
 *
 */
@Data
public class ChangePasswordResetDTO {

	private String email;
	private String password;
	private String newPassword;
	private String confirmPassword;



}
