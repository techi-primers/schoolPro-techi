package com.zak.pro.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.entity.Company;
import com.zak.pro.entity.Project;
import com.zak.pro.entity.Role;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
import com.zak.pro.repository.AccountRepository;
import com.zak.pro.repository.CompanyRepository;
import com.zak.pro.repository.ProjectRepository;
import com.zak.pro.repository.RoleRepository;
import com.zak.pro.service.CompanyService;
import com.zak.pro.util.PasswordUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class CompanyImplService implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private PasswordUtil util;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public void addCategories(List<Category> intrests) throws NoSuchMessageException, CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Company company = this.companyRepository.findByEmail(email);
		if (company == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		company.setInterests(intrests);
		this.companyRepository.save(company);
	}

	@Override
	public List<Company> getCompany() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Category> interests = this.projectRepository.findByStudent_email(email).stream().map(Project::getCategory)
				.collect(Collectors.toList());
		if ((interests == null) || interests.isEmpty()) {
			return this.companyRepository.findAll();
		}
		List<Company> companies = new ArrayList<Company>();
		List<Company> companyByCategory = new ArrayList<Company>();
		for (Category interest : interests) {
			companyByCategory = this.companyRepository.findByInterestsContaining(interest);
			for (Company company : companyByCategory) {
				if (!companies.contains(company)) {
					companies.add(company);
				}
			}
		}
		return companies;
	}

	@Transactional
	@Override
	public Company registerCompany(CompanyDTO companydto) throws CustomException {
		if ((companydto != null) && (this.accountRepository.findByEmail(companydto.getEmail()) == null)
				&& (this.accountRepository.findByMobile(companydto.getMobile()) == null)) {
			Company company = this.modelMapper.map(companydto, Company.class);
			if (!company.isAcceptCGU()) {
				throw new CustomException(
						this.messageSource.getMessage("cgu.not.accepted", null, LocaleContextHolder.getLocale()));

			}
			if (!this.util.isPasswordValid(company.getPassword())) {
				throw new CustomException(
						this.messageSource.getMessage("password.not.compliant", null, LocaleContextHolder.getLocale()));
			}
			company.setPassword(this.bcrypt.encode(company.getPassword()));
			Role role = this.roleRepository.findByRole(companydto.getRole());
			if (role == null) {
				throw new CustomException(
						this.messageSource.getMessage("role.not.found", null, LocaleContextHolder.getLocale()));

			}
			company.setRole(role);
			company.setFirstTime(true);
			return this.companyRepository.save(company);
		}
		throw new CustomException(
				this.messageSource.getMessage("email.phone.already.exist", null, LocaleContextHolder.getLocale()));

	}
}
