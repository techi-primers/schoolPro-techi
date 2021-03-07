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

import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.entity.Account;
import com.zak.pro.entity.Company;
import com.zak.pro.entity.Investor;
import com.zak.pro.entity.Project;
import com.zak.pro.entity.Role;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
import com.zak.pro.repository.AccountRepository;
import com.zak.pro.repository.CompanyRepository;
import com.zak.pro.repository.InvestorRepository;
import com.zak.pro.repository.ProjectRepository;
import com.zak.pro.repository.RoleRepository;
import com.zak.pro.service.CompanyService;
import com.zak.pro.service.InvestorService;
import com.zak.pro.util.PasswordUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class InvestorImplService implements InvestorService {

	@Autowired
	private InvestorRepository investorRepository;

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
	private CompanyService companyService;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public void addCategories(List<Category> intrests) throws NoSuchMessageException, CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Investor investor = this.investorRepository.findByEmail(email);
		if (investor == null) {
			this.companyService.addCategories(intrests);
		} else {
			investor.setInterests(intrests);
			this.investorRepository.save(investor);
		}
	}

	@Override
	public List<Investor> getInvestors() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Category> interests = this.projectRepository.findByStudent_email(email).stream().map(Project::getCategory)
				.collect(Collectors.toList());
		if ((interests == null) || interests.isEmpty()) {
			return new ArrayList<Investor>();
		}
		List<Investor> investors = new ArrayList<Investor>();
		List<Investor> investorByCategory = new ArrayList<Investor>();
		for (Category interest : interests) {
			investorByCategory = this.investorRepository.findByInterestsContaining(interest);
			for (Investor investor : investorByCategory) {
				if (!investors.contains(investor)) {
					investors.add(investor);
				}
			}
		}
		return investors;
	}

	@Transactional
	@Override
	public Investor registerInvestor(InvestorDTO investordto) throws CustomException {
		if ((investordto != null) && (this.accountRepository.findByEmail(investordto.getEmail()) == null)
				&& (this.accountRepository.findByMobile(investordto.getMobile()) == null)) {
			Investor investor = this.modelMapper.map(investordto, Investor.class);
			if (!investor.isAcceptCGU()) {
				throw new CustomException(
						this.messageSource.getMessage("cgu.not.accepted", null, LocaleContextHolder.getLocale()));

			}
			if (!this.util.isPasswordValid(investor.getPassword())) {
				throw new CustomException(
						this.messageSource.getMessage("password.not.compliant", null, LocaleContextHolder.getLocale()));
			}
			investor.setPassword(this.bcrypt.encode(investor.getPassword()));
			Role role = this.roleRepository.findByRole(investordto.getRole());
			if (role == null) {
				throw new CustomException(
						this.messageSource.getMessage("role.not.found", null, LocaleContextHolder.getLocale()));

			}
			investor.setRole(role);
			investor.setFirstTime(true);
			return this.investorRepository.save(investor);
		}
		throw new CustomException(
				this.messageSource.getMessage("email.phone.already.exist", null, LocaleContextHolder.getLocale()));

	}

	@Override
	public List<ProjectDTO> getFavoritProject() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account instanceof Investor) {
			return ((Investor) account).getFavorits().stream()
					.map(project -> this.modelMapper.map(project, ProjectDTO.class)).collect(Collectors.toList());
		} else {
			return ((Company) account).getFavorits().stream()
					.map(project -> this.modelMapper.map(project, ProjectDTO.class)).collect(Collectors.toList());
		}
	}

	@Override
	public void bookmarkProjectForInvestorAndCompany(Long id) throws CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		Project p = this.projectRepository.findById(id).orElseThrow(() -> new CustomException(
				this.messageSource.getMessage("project.not.found", null, LocaleContextHolder.getLocale())));
		if (account instanceof Investor) {
			((Investor) account).getFavorits().add(p);
			this.investorRepository.save((Investor) account);
		} else {
			((Company) account).getFavorits().add(p);
			this.companyRepository.save((Company) account);
		}
	}

	@Override
	public void unbookmarkProjectForInvestorAndCompany(Long id) throws CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account instanceof Investor) {
			for (Project project : ((Investor) account).getFavorits()) {
				if (project.getId() == id) {
					((Investor) account).getFavorits().remove(project);
					this.investorRepository.save((Investor) account);
					break;
				}
			}
		} else {
			for (Project project : ((Company) account).getFavorits()) {
				if (project.getId() == id) {
					((Company) account).getFavorits().remove(project);
					this.companyRepository.save((Company) account);
					break;
				}
			}
		}
	}
}
