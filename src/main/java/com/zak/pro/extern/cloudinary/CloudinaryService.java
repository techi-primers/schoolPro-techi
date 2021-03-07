package com.zak.pro.extern.cloudinary;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.CloudinaryResponseDTO;
import com.zak.pro.enumartion.AttachementType;
import com.zak.pro.exception.CustomException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public interface CloudinaryService {

	CloudinaryResponseDTO uploadAttachement(MultipartFile file) throws IOException, CustomException;

	void deleteAttachement(String public_id, AttachementType type) throws IOException;

	void deleteAttachementAsync(String public_id, AttachementType type) throws IOException;
}
