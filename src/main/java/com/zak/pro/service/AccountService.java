package com.zak.pro.service;

import java.io.IOException;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.AccountDTO;
import com.zak.pro.dto.UpdateAccountDTO;
import com.zak.pro.dto.UploadImageDTO;
import com.zak.pro.exception.CustomException;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public interface AccountService {

	void updatePushRegistrationId(String pushRegistrationId) throws NoSuchMessageException, CustomException;

	UploadImageDTO uploadProfilImage(MultipartFile file) throws CustomException, IOException;

	void updateProfilImage(MultipartFile file) throws CustomException, IOException;

	AccountDTO updateProfil(UpdateAccountDTO accountDTO) throws Exception;

	AccountDTO getProfilDetail() throws NoSuchMessageException, CustomException;

	void deleteProfile() throws IOException;

}
