package com.zak.pro.extern.firebase;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public enum NotificationParameter {

	SOUND("default"), COLOR("#6c0a87");

	private String value;

	NotificationParameter(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
