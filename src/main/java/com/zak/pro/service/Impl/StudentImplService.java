package com.zak.pro.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zak.pro.dto.CompanyDTO;
import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.StudentDTO;
import com.zak.pro.entity.Account;
import com.zak.pro.entity.Company;
import com.zak.pro.entity.Investor;
import com.zak.pro.entity.Role;
import com.zak.pro.entity.Student;
import com.zak.pro.exception.CustomException;
import com.zak.pro.repository.AccountRepository;
import com.zak.pro.repository.RoleRepository;
import com.zak.pro.repository.StudentRepository;
import com.zak.pro.service.StudentService;
import com.zak.pro.util.PasswordUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class StudentImplService implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

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

	@Transactional
	@Override
	public Student registerStudent(StudentDTO studentdto) throws CustomException {
		if ((studentdto != null) && (this.accountRepository.findByEmail(studentdto.getEmail()) == null)
				&& (this.accountRepository.findByMobile(studentdto.getMobile()) == null)) {
			Student student = this.modelMapper.map(studentdto, Student.class);
			if (!student.isAcceptCGU()) {
				throw new CustomException(
						this.messageSource.getMessage("cgu.not.accepted", null, LocaleContextHolder.getLocale()));

			}
			if (!this.util.isPasswordValid(student.getPassword())) {
				throw new CustomException(
						this.messageSource.getMessage("password.not.compliant", null, LocaleContextHolder.getLocale()));

			}
			student.setPassword(this.bcrypt.encode(student.getPassword()));
			Role role = this.roleRepository.findByRole(studentdto.getRole());
			if (role == null) {
				throw new CustomException(
						this.messageSource.getMessage("role.not.found", null, LocaleContextHolder.getLocale()));

			}
			student.setRole(role);
			student.setFirstTime(true);
			return this.studentRepository.save(student);
		}
		throw new CustomException(
				this.messageSource.getMessage("email.phone.already.exist", null, LocaleContextHolder.getLocale()));

	}

	@Override
	public List<InvestorDTO> getFavoriteInvestors() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account instanceof Student) {
			return ((Student) account).getFavoriteInvestors().stream()
					.map(in -> this.modelMapper.map(in, InvestorDTO.class)).collect(Collectors.toList());
		} else {
			throw new AccessDeniedException(
					this.messageSource.getMessage("not.authorize", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<CompanyDTO> getFavoriteCompanies() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		if (account instanceof Student) {
			return ((Student) account).getFavoriteCompanies().stream()
					.map(com -> this.modelMapper.map(com, CompanyDTO.class)).collect(Collectors.toList());
		} else {
			throw new AccessDeniedException(
					this.messageSource.getMessage("not.authorize", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void bookMarkedInvestorOrCompany(Long id) throws CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		Account account2 = this.accountRepository.findById(id).orElseThrow(() -> new CustomException(
				this.messageSource.getMessage("id.not.found", null, LocaleContextHolder.getLocale())));
		if (account instanceof Student) {
			Long result1 = ((Student) account).getFavoriteInvestors().stream().map(Investor::getId).filter(i -> i == id)
					.findFirst().orElse(null);
			if (result1 == null) {
				Long result2 = ((Student) account).getFavoriteCompanies().stream().map(Company::getId)
						.filter(i -> i == id).findFirst().orElse(null);
				if (result2 != null) {
					throw new CustomException(this.messageSource.getMessage("investor.company.already.bookmarked", null,
							LocaleContextHolder.getLocale()));
				}
			} else {
				throw new CustomException(this.messageSource.getMessage("investor.company.already.bookmarked", null,
						LocaleContextHolder.getLocale()));
			}

			if (account2 instanceof Investor) {
				((Student) account).getFavoriteInvestors().add((Investor) account2);
				this.accountRepository.save(account);
			} else if (account2 instanceof Company) {
				((Student) account).getFavoriteCompanies().add((Company) account2);
				this.accountRepository.save(account);
			} else {
				throw new CustomException(this.messageSource.getMessage("student.bookmark.impossible", null,
						LocaleContextHolder.getLocale()));
			}
		} else {
			throw new AccessDeniedException(
					this.messageSource.getMessage("not.authorize", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void unbookMarkInvestorOrCompany(Long id) throws Exception {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		boolean done = false;
		if (account instanceof Student) {
			for (Investor investor : ((Student) account).getFavoriteInvestors()) {
				if (investor.getId() == id) {
					((Student) account).getFavoriteInvestors().remove(investor);
					this.accountRepository.save(account);
					done = true;
					break;
				}
			}
			if (!done) {
				for (Company company : ((Student) account).getFavoriteCompanies()) {
					if (company.getId() == id) {
						((Student) account).getFavoriteCompanies().remove(company);
						this.accountRepository.save(account);
						done = true;
						break;
					}
				}
			}
			if (!done) {
				throw new CustomException(this.messageSource.getMessage("investor.company.not.found", null,
						LocaleContextHolder.getLocale()));
			}
		}
	}
}
