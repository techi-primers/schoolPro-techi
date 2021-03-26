package com.zak.pro.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.zak.pro.entity.*;
import com.zak.pro.repository.*;
import org.apache.catalina.UserDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zak.pro.dto.InvestorDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
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

    private Logger logger = LogManager.getLogger(InvestorImplService.class);

    @Autowired
    private InvesterProjectRepository investorProjectRepository;

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
			investor.setPublic_id(investordto.getPublicId());
			if (investor.getPublic_id()==null || investor.getPublic_id() == "") {
				throw new CustomException(
						this.messageSource.getMessage("publicid.null", null, LocaleContextHolder.getLocale()));

			}
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

    @Override
    public ResponseEntity assignProjectToInvester(Long projectId) {

		if(projectId!=null) {
			Optional<Project> project = projectRepository.findById(projectId);
			return project.map(pro -> {

				String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(email!=null) {
					Account account = this.accountRepository.findByEmail(email);

					return Optional.ofNullable(account).map(acc-> {
                        Long accountId = acc.getId();
                        String investerEmail = acc.getEmail();
                        InvesterProject investPro = new InvesterProject();
                        investPro.setInvesterId(accountId);
                        investPro.setInvesterEmail(investerEmail);
                        investPro.setProjectId(projectId);
                        try{
                            InvesterProject savedObj;
                            savedObj = this.investorProjectRepository.save(investPro);
                            logger.info("invester projects saved!!");
                            return new ResponseEntity(savedObj,HttpStatus.OK);
                        }catch (Exception e ) {
                            e.printStackTrace();
                            logger.info("catch when saving investerproject...");
                            return new ResponseEntity(HttpStatus.NOT_FOUND);
                        }

                    }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));

				} else {
					return new ResponseEntity(HttpStatus.NOT_FOUND);
				}

			}).orElse( new ResponseEntity(HttpStatus.NOT_FOUND));
		}else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

    }

    @Override
    public ResponseEntity getInvesterAssignedProjects() {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return Optional.ofNullable(email).map(rec -> {
			Account account = this.accountRepository.findByEmail(email);
			return Optional.ofNullable(account).map(acc -> {
				List<InvesterProject> investedProjects = this.investorProjectRepository.findByInvesterId(acc.getId());
				return new ResponseEntity(investedProjects, HttpStatus.OK);
			}).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
		}).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));

    }
}
