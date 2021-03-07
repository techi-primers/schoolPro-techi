package com.zak.pro.service.Impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.AccountDTO;
import com.zak.pro.dto.CloudinaryResponseDTO;
import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.StudentDTO;
import com.zak.pro.dto.UpdateAccountDTO;
import com.zak.pro.dto.UploadImageDTO;
import com.zak.pro.entity.Account;
import com.zak.pro.entity.Attachement;
import com.zak.pro.entity.Company;
import com.zak.pro.entity.Investor;
import com.zak.pro.entity.Project;
import com.zak.pro.entity.Student;
import com.zak.pro.enumartion.AttachementType;
import com.zak.pro.exception.CustomException;
import com.zak.pro.extern.cloudinary.CloudinaryService;
import com.zak.pro.repository.AccountRepository;
import com.zak.pro.repository.ProjectRepository;
import com.zak.pro.service.AccountService;
import com.zak.pro.service.AuthenticationService;
import com.zak.pro.util.FileUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class AccountImplService implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void updatePushRegistrationId(String pushRegistrationId) throws NoSuchMessageException, CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		account.setPushRegistrationId(pushRegistrationId);
		this.accountRepository.save(account);
	}

	@Override
	public UploadImageDTO uploadProfilImage(MultipartFile file) throws CustomException, IOException {
		this.fileUtil.isImage(file);
		if (file.getSize() > 2097152) {
			throw new CustomException(
					this.messageSource.getMessage("max.image.size.exceeded", null, LocaleContextHolder.getLocale()));
		}
		CloudinaryResponseDTO dto = this.cloudinaryService.uploadAttachement(file);
		UploadImageDTO imageDTO = new UploadImageDTO();
		imageDTO.setProfilImage(dto.getSecure_url());
		imageDTO.setPublicId(dto.getPublic_id());
		return imageDTO;
	}

	@Override
	public void updateProfilImage(MultipartFile file) throws CustomException, IOException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		this.fileUtil.isImage(file);
		if (file.getSize() > 2097152) {
			throw new CustomException(
					this.messageSource.getMessage("max.image.size.exceeded", null, LocaleContextHolder.getLocale()));
		}
		if ((account.getProfilImage() != null) && !account.getProfilImage().equals("")) {
			this.cloudinaryService.deleteAttachement(account.getProfilImage(), AttachementType.IMAGE);
		}
		this.cloudinaryService.deleteAttachement(account.getPublic_id(), AttachementType.IMAGE);
		CloudinaryResponseDTO dto = this.cloudinaryService.uploadAttachement(file);
		account.setProfilImage(dto.getSecure_url());
		account.setPublic_id(dto.getPublic_id());
		this.accountRepository.save(account);
	}

	@Override
	public AccountDTO updateProfil(UpdateAccountDTO accountDTO) throws Exception {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		if (!email.equals(accountDTO.getEmail())
				&& (this.accountRepository.findByEmail(accountDTO.getEmail()) != null)) {
			throw new CustomException(
					this.messageSource.getMessage("email.phone.already.exist", null, LocaleContextHolder.getLocale()));
		}
		if (!account.getMobile().equals(accountDTO.getMobile())
				&& (this.accountRepository.findByMobile(accountDTO.getMobile()) != null)) {
			throw new CustomException(
					this.messageSource.getMessage("email.phone.already.exist", null, LocaleContextHolder.getLocale()));
		}
		account.setEmail(accountDTO.getEmail());
		account.setMobile(accountDTO.getMobile());
		account.setAddress(accountDTO.getAddress());
		account.setCountry(accountDTO.getCountry());
		account.setDescription(accountDTO.getDescription());
		account.setWebsiteUrl(accountDTO.getWebsiteUrl());
		account.setAllowContact(accountDTO.getAllowContact());
		String jwt;
		if (account instanceof Company) {
			((Company) account).setCompanyName(accountDTO.getCompanyName());
			this.accountRepository.save(account);
			CompanyDTO cmp = this.modelMapper.map(account, CompanyDTO.class);
			jwt = this.authenticationService.updateToken(accountDTO.getEmail());
			cmp.setJwt(jwt);
			return cmp;
		} else if (account instanceof Investor) {
			((Investor) account).setFirstName(accountDTO.getFirstName());
			((Investor) account).setLastName(accountDTO.getLastName());
			((Investor) account).setAge(accountDTO.getAge());
			this.accountRepository.save(account);
			InvestorDTO invst = this.modelMapper.map(account, InvestorDTO.class);
			jwt = this.authenticationService.updateToken(accountDTO.getEmail());
			invst.setJwt(jwt);
			return invst;
		} else {
			((Student) account).setFirstName(accountDTO.getFirstName());
			((Student) account).setLastName(accountDTO.getLastName());
			((Student) account).setAge(accountDTO.getAge());
			((Student) account).setBranch(accountDTO.getBranch());
			((Student) account).setCollege(accountDTO.getCollege());
			this.accountRepository.save(account);
			StudentDTO std = this.modelMapper.map(account, StudentDTO.class);
			jwt = this.authenticationService.updateToken(accountDTO.getEmail());
			std.setJwt(jwt);
			return std;
		}
	}

	@Override
	public AccountDTO getProfilDetail() throws NoSuchMessageException, CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		if (account instanceof Company) {
			return this.modelMapper.map(account, CompanyDTO.class);
		} else if (account instanceof Investor) {
			return this.modelMapper.map(account, InvestorDTO.class);
		} else {
			return this.modelMapper.map(account, StudentDTO.class);
		}
	}

	@Override
	public void deleteProfile() throws IOException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account instanceof Student) {
			List<Project> projects = this.projectRepository.findByStudent_email(email);
			Map<String, AttachementType> attachements = new HashMap<String, AttachementType>();
			for (Project project : projects) {
				for (Attachement attachement : project.getAttachements()) {
					attachements.put(attachement.getPublic_id(), attachement.getType());
				}
				this.projectRepository.delete(project);
			}
			for (String key : attachements.keySet()) {
				this.cloudinaryService.deleteAttachementAsync(key, attachements.get(key));
			}
			this.accountRepository.delete(account);

		} else {
			this.accountRepository.delete(account);
		}
	}

}
