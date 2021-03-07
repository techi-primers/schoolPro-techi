package com.zak.pro.exception;
/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class CustomException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private final String message;

	public CustomException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	
	
}
