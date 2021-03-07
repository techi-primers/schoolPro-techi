package com.zak.pro.extern.cloudinary;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.zak.pro.dto.CloudinaryResponseDTO;
import com.zak.pro.enumartion.AttachementType;
import com.zak.pro.exception.CustomException;
import com.zak.pro.util.FileUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class CloudinaryImplService implements CloudinaryService {

	private static final Logger logger = LoggerFactory.getLogger(CloudinaryImplService.class);

	@Value("${cloudinary.cloud_name}")
	private String cloud_name;

	@Value("${cloudinary.api_key}")
	private String api_key;

	@Value("${cloudinary.api_secret}")
	private String api_secret;

	@Autowired
	private FileUtil fileUtil;

	@SuppressWarnings("unchecked")
	@Override
	public CloudinaryResponseDTO uploadAttachement(MultipartFile file) throws IOException, CustomException {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", this.cloud_name, "api_key", this.api_key,
				"api_secret", this.api_secret));
		File tempFile = this.fileUtil.multipartToFile(file, file.getOriginalFilename());
		Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile,
				ObjectUtils.asMap("resource_type", "auto"));
		CloudinaryImplService.logger.info(uploadResult.toString());
		this.fileUtil.deleteTempFile(tempFile);
		CloudinaryResponseDTO response = new CloudinaryResponseDTO();
		response.setSecure_url((String) uploadResult.get("secure_url"));
		response.setBytes((int) uploadResult.get("bytes"));
		response.setPublic_id((String) uploadResult.get("public_id"));
		response.setFormat((String) uploadResult.get("format"));
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAttachement(String public_id, AttachementType type) throws IOException {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", this.cloud_name, "api_key", this.api_key,
				"api_secret", this.api_secret));
		Map<String, Object> deleteResult;
		if (type.equals(AttachementType.IMAGE)) {
			deleteResult = cloudinary.uploader().destroy(this.buildPublicIdStructure(public_id, type),
					ObjectUtils.emptyMap());
			CloudinaryImplService.logger.info(deleteResult.toString());

		} else if (type.equals(AttachementType.AUDIO)) {
			deleteResult = cloudinary.uploader().destroy(this.buildPublicIdStructure(public_id, type),
					ObjectUtils.asMap("resource_type", "video"));
			CloudinaryImplService.logger.info(deleteResult.toString());

		} else if (type.equals(AttachementType.FILE)) {
			deleteResult = cloudinary.uploader().destroy(this.buildPublicIdStructure(public_id, type),
					ObjectUtils.emptyMap());
			CloudinaryImplService.logger.info(deleteResult.toString());

		} else {
			deleteResult = new HashMap<String, Object>();
			deleteResult.put(type.name(), "not suported");
			CloudinaryImplService.logger.info(deleteResult.toString());

		}
	}

	@Async
	@Override
	public void deleteAttachementAsync(String public_id, AttachementType type) throws IOException {
		this.deleteAttachement(public_id, type);
	}

	private String buildPublicIdStructure(String public_id, AttachementType type) {
		if (type.equals(AttachementType.AUDIO)) {
			public_id = public_id.replace(".mp3", "");
			public_id = public_id.replace(".MP3", "");
		} else if (type.equals(AttachementType.IMAGE)) {
			public_id = public_id.replace(".jpg", "");
			public_id = public_id.replace(".JPG", "");
			public_id = public_id.replace(".jpeg", "");
			public_id = public_id.replace(".JPEG", "");
			public_id = public_id.replace(".png", "");
			public_id = public_id.replace(".PNG", "");
		}
		return public_id;
	}

}
