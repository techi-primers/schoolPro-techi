package com.zak.pro.dto;

import java.util.Map;

public class MailDTO {

	private String from;
	private String to;
	private String subject;
	private String text;
	private String img;
	private String template;
	private Map<String, Object> props;

	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return this.from;
	}

	public MailDTO() {
		super();
	}

	public Map<String, Object> getProps() {
		return this.props;
	}

	public void setProps(Map<String, Object> props) {
		this.props = props;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
