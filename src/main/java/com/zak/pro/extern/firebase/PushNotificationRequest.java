package com.zak.pro.extern.firebase;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class PushNotificationRequest {

	private String title;
	private String message;
	private String topic;
	private String token;
	private String imageUrl;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public PushNotificationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
