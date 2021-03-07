package com.zak.pro.service;

import javax.mail.MessagingException;

import com.zak.pro.dto.MailDTO;

public interface MailService {

	void sendMail(MailDTO mail) throws MessagingException;
}
