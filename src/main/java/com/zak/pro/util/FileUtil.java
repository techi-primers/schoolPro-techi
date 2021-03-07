package com.zak.pro.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.exception.CustomException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Component
public class FileUtil {

	@Autowired
	private MessageSource messageSource;

	public File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
		File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
		multipart.transferTo(tempFile);
		return tempFile;
	}

	public void deleteTempFile(File tempFile) throws IllegalStateException, IOException {
		tempFile.delete();
	}

	public boolean isFileListValid(MultipartFile[] files) throws CustomException {
		for (MultipartFile file : files) {
			if (!Pattern.matches("([^\\s]+(\\.(?i)(jpg|jpeg|png|mp3|MP3|JPG|JPEG|PNG|pdf))$)",
					file.getOriginalFilename().replace(" ", "")) || file.isEmpty()) {
				throw new CustomException(this.messageSource.getMessage("file.format.not.supported", null,
						LocaleContextHolder.getLocale()));
			}
		}
		return true;
	}

	public boolean isImage(MultipartFile file) throws CustomException {
		if (!Pattern.matches("([^\\s]+(\\.(?i)(jpg|jpeg|png|JPG|JPEG|PNG))$)",
				file.getOriginalFilename().replace(" ", "")) || file.isEmpty()) {
			throw new CustomException(
					this.messageSource.getMessage("file.format.not.supported", null, LocaleContextHolder.getLocale()));
		}
		return true;
	}

	public boolean isFileValid(MultipartFile file) throws CustomException {
		if (!Pattern.matches("([^\\s]+(\\.(?i)(jpg|jpeg|png|mp3|MP3|JPG|JPEG|PNG|pdf))$)",
				file.getOriginalFilename().replace(" ", "")) || file.isEmpty()) {
			throw new CustomException(
					this.messageSource.getMessage("file.format.not.supported", null, LocaleContextHolder.getLocale()));
		}
		return true;
	}

}
