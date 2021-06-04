package com.zak.pro.service.Impl;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.BooleanBuilder;
import static java.util.Comparator.comparingLong;
import com.zak.pro.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zak.pro.dto.CloudinaryResponseDTO;
import com.zak.pro.dto.ProjectAddDTO;
import com.zak.pro.dto.ProjectDTO;
import com.zak.pro.dto.ProjectUpdateDTO;
import com.zak.pro.enumartion.AttachementType;
import com.zak.pro.enumartion.Category;
import com.zak.pro.exception.CustomException;
import com.zak.pro.extern.cloudinary.CloudinaryService;
import com.zak.pro.extern.firebase.FCMService;
import com.zak.pro.extern.firebase.PushNotificationRequest;
import com.zak.pro.repository.AccountRepository;
import com.zak.pro.repository.CompanyRepository;
import com.zak.pro.repository.InvestorRepository;
import com.zak.pro.repository.ProjectRepository;
import com.zak.pro.repository.StudentRepository;
import com.zak.pro.service.ProjectService;
import com.zak.pro.util.FileUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class ProjectImplService implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private InvestorRepository investorRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private FCMService fcmService;

	private Logger logger = LogManager.getLogger(ProjectImplService.class);

	/**
	 * if categories is null return all projects if categories filled return all
	 * projects filter by category list if investor or company has interests return
	 * all project filter by intrest
	 */

	@Override
	public List<ProjectDTO> getProjects(List<Category> categories) throws NoSuchMessageException, CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountRepository.findByEmail(email);
		Investor investor = null;
		Company company = null;
		List<Project> projects = null;
		if (account instanceof Investor) {
			investor = (Investor) account;
		} else if (account instanceof Company) {
			company = (Company) account;
		} else {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		projects = this.getProjectByInvestorTypeAndCategory(investor, company, categories);
		return projects.stream().map(project -> this.modelMapper.map(project, ProjectDTO.class))
				.collect(Collectors.toList());
	}

	private List<Project> getProjectByInvestorTypeAndCategory(Investor investor, Company company,
			List<Category> categories) {
		List<Project> projects = null;
		if (investor != null) {
			if (investor.getInterests().contains(Category.ANY)) {
				projects = this.projectRepository.findAll();
			} else if ((investor.getInterests() != null) && (investor.getInterests().size() != 0)) {
				projects = this.projectRepository.findByCategoryIn(investor.getInterests());
			} else {
				projects = ((categories == null) || (categories.size() == 0)) ? this.projectRepository.findAll()
						: this.projectRepository.findByCategoryIn(categories);
			}
		} else if (company != null) {
			if (company.getInterests().contains(Category.ANY)) {
				projects = this.projectRepository.findAll();
			} else if ((company.getInterests() != null) && (company.getInterests().size() != 0)) {
				projects = this.projectRepository.findByCategoryIn(company.getInterests());
			} else {
				projects = ((categories == null) || (categories.size() == 0)) ? this.projectRepository.findAll()
						: this.projectRepository.findByCategoryIn(categories);
			}
		}
		return projects;
	}

	@Override
	public List<ProjectDTO> getProjectsByStudent(Long id) {
		List<Project> projects = this.projectRepository.findByStudent_id(id);
		return projects.stream().map(project -> this.modelMapper.map(project, ProjectDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<ProjectDTO> getProjectsForConnectedStudent() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Project> projects = this.projectRepository.findByStudent_email(email);
		return projects.stream().map(project -> this.modelMapper.map(project, ProjectDTO.class))
				.peek(projectDto -> projectDto.setStudent(null)).collect(Collectors.toList());
	}

	@Override
	public ProjectDTO createProject(ProjectAddDTO projectDto) throws NoSuchMessageException, CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Student student = this.studentRepository.findByEmail(email);
		if (student == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		Project project = this.modelMapper.map(projectDto, Project.class);
		project.setStudent(student);
		project = this.projectRepository.save(project);
		project.setStudent(null);
		return this.modelMapper.map(project, ProjectDTO.class);
	}

	@Override
	public ProjectDTO updateProject(ProjectUpdateDTO projectDto) throws CustomException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Student student = this.studentRepository.findByEmail(email);
		if (student == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		Project existedProject = this.projectRepository.findById(projectDto.getId())
				.orElseThrow(() -> new CustomException(this.messageSource.getMessage("update.project.not.found", null,
						LocaleContextHolder.getLocale())));
		Project project = this.modelMapper.map(projectDto, Project.class);
		project.setStudent(student);
		project.setAttachements(existedProject.getAttachements());
		project = this.projectRepository.save(project);
		project.setStudent(null);
		return this.modelMapper.map(project, ProjectDTO.class);
	}

	@Transactional
	@Override
	public void uploadAttachements(Long id, MultipartFile[] files, int show) throws Exception {
		this.isFileLengthAuthorized(files);
		this.fileUtil.isFileListValid(files);
		Project project = this.projectRepository.findById(id).orElse(null);
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Project> projects = this.projectRepository.findByStudent_email(email);
		if ((project != null) && projects.contains(project)) {
			List<Attachement> attachements = project.getAttachements();
			this.isAttachementLengthAuthorized(attachements, files.length);
			this.isAttachementsizeAuthorized(attachements, this.calculateFilesSize(files));
			for (int i = 0; i < files.length; i++) {
				CloudinaryResponseDTO dto = this.cloudinaryService.uploadAttachement(files[i]);
				Attachement attachement = new Attachement();
				AttachementType type = this.getAttachementType(dto.getSecure_url());
				attachement.setType(type);
				if (show == i) {
					attachement.setShow(true);
				}
				attachements.add(this.buildAttachement(dto, attachement));
				project.setAttachements(attachements);
				this.projectRepository.save(project);
			}
			this.sendPushNotificationToInvestor(project);
		} else {
			throw new CustomException(
					this.messageSource.getMessage("student.project.not.found", null, LocaleContextHolder.getLocale()));
		}
	}

	@Async
	private void sendPushNotificationToInvestor(Project project) {
		List<Investor> investors = this.investorRepository.findByInterestsContaining(project.getCategory());
		List<Company> companies = this.companyRepository.findByInterestsContaining(project.getCategory());
		PushNotificationRequest notificationRequest = new PushNotificationRequest();
		notificationRequest.setTitle("A new Project has been added !!");
		notificationRequest.setMessage(project.getName());
		String imageUrl = "";
		if (project.getAttachements() != null) {
			for (Attachement attachement : project.getAttachements()) {
				if (attachement.isShow()) {
					imageUrl = attachement.getUrl();
				}
			}
		}
		notificationRequest.setImageUrl(imageUrl);
		for (Company company : companies) {
			if (company.getPushRegistrationId() != null) {
				notificationRequest.setToken(company.getPushRegistrationId());
				try {
					this.fcmService.sendMessageToToken(notificationRequest);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		for (Investor investor : investors) {
			if (investor.getPushRegistrationId() != null) {
				notificationRequest.setToken(investor.getPushRegistrationId());
				try {
					this.fcmService.sendMessageToToken(notificationRequest);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/*
	 * @Override
	 * 
	 * @Transactional public Attachement updateAttachement(Long id, MultipartFile
	 * file) throws CustomException, IOException { String email = (String)
	 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 * List<Project> projects = this.projectRepository.findByStudent_email(email);
	 * for (Project project : projects) {
	 * this.isAttachementsizeAuthorized(project.getAttachements(), (int)
	 * file.getSize()); for (Attachement attachement : project.getAttachements()) {
	 * if (attachement.getId() == id) { this.fileUtil.isFileValid(file);
	 * CloudinaryResponseDTO dto = this.cloudinaryService.uploadAttachement(file);
	 * this.cloudinaryService.deleteAttachement(attachement.getPublic_id(),
	 * attachement.getType());
	 * attachement.setType(this.getAttachementType(dto.getSecure_url())); return
	 * this.attachementRepository.save(this.buildAttachement(dto, attachement)); } }
	 * } throw new CustomException("Attachement does not existe for this student");
	 * }
	 */

	@Override
	@Transactional
	public boolean deleteAttachement(Long id) throws CustomException, IOException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Project> projects = this.projectRepository.findByStudent_email(email);
		for (Project project : projects) {
			for (Attachement attachement : project.getAttachements()) {
				if (attachement.getId() == id) {
					this.cloudinaryService.deleteAttachement(attachement.getPublic_id(), attachement.getType());
					project.getAttachements().remove(attachement);
					this.projectRepository.save(project);
					return true;
				}
			}
		}
		throw new CustomException(
				this.messageSource.getMessage("student.attachments.not.found", null, LocaleContextHolder.getLocale()));

	}

	@Override
	public void deleteProject(Long id) throws CustomException, IOException {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Project> projects = this.projectRepository.findByStudent_email(email);
		Project project = this.projectRepository.findById(id).orElseThrow(() -> new CustomException(
				this.messageSource.getMessage("student.project.not.found", null, LocaleContextHolder.getLocale())));
		if (!projects.contains(project)) {
			new CustomException(
					this.messageSource.getMessage("student.project.not.found", null, LocaleContextHolder.getLocale()));
		}
		Map<String, AttachementType> attachements = new HashMap<String, AttachementType>();
		for (Attachement attachement : project.getAttachements()) {
			attachements.put(attachement.getPublic_id(), attachement.getType());
		}
		this.projectRepository.deleteById(id);
		for (String key : attachements.keySet()) {
			this.cloudinaryService.deleteAttachementAsync(key, attachements.get(key));
		}
	}

	private AttachementType getAttachementType(String url) {
		if (url.endsWith(".mp3") || url.endsWith("MP3")) {
			return AttachementType.AUDIO;
		} else if (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".jpeg") || url.endsWith(".JPG")
				|| url.endsWith(".PNG") || url.endsWith(".JPEG")) {
			return AttachementType.IMAGE;
		}
		return AttachementType.FILE;
	}

	private boolean isFileLengthAuthorized(MultipartFile[] files) throws CustomException {
		if (files.length >= 5) {
			throw new CustomException(
					this.messageSource.getMessage("max.file.exceeded", null, LocaleContextHolder.getLocale()));
		}
		return true;
	}

	private boolean isAttachementLengthAuthorized(List<Attachement> attachements, int attachementLength)
			throws CustomException {
		if ((attachements.size() + attachementLength) > 5) {
			throw new CustomException(
					this.messageSource.getMessage("max.attachment.exceeded", null, LocaleContextHolder.getLocale()));
		}
		return true;
	}

	private boolean isAttachementsizeAuthorized(List<Attachement> attachements, int attachementSize)
			throws CustomException {
		int size = 0;
		for (Attachement attachement : attachements) {
			size += attachement.getSize();
		}
		if ((size + attachementSize) > 20971520) {
			throw new CustomException(
					this.messageSource.getMessage("max.size.exceeded", null, LocaleContextHolder.getLocale()));

		}
		return true;
	}

	private int calculateFilesSize(MultipartFile[] files) {
		int size = 0;
		for (MultipartFile file : files) {
			size += file.getSize();
		}
		return size;
	}

	private Attachement buildAttachement(CloudinaryResponseDTO dto, Attachement attachement) {
		attachement.setUrl(dto.getSecure_url());
		attachement.setSize(dto.getBytes());
		attachement.setPublic_id(dto.getPublic_id());
		attachement.setFormat(dto.getFormat());
		return attachement;
	}

	@Override
	public List<Project> searchProjectsByKeyWord(String keyword) {

		List<Project> projectList = null;
		try {
			BooleanBuilder booleanBuilder = new BooleanBuilder();
			QProject qProject = QProject.project;

			if(keyword !=null || !keyword.isEmpty()) {
				booleanBuilder.or(qProject.description.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.name.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.videoUrl.containsIgnoreCase(keyword));

				booleanBuilder.or(qProject.student.firstName.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.lastName.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.branch.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.College.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.age.equalsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.address.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.country.containsIgnoreCase(keyword));
				booleanBuilder.or(qProject.student.email.containsIgnoreCase(keyword));
				//booleanBuilder.or(qProject.groupMember.contains(keyword));
			}

			projectList =
					(List<Project>) this.projectRepository.findAll(booleanBuilder);


			for( Project pro : this.projectRepository.findAll()) {
					if(pro.getStatus().name().toLowerCase().equalsIgnoreCase(keyword.toLowerCase())) {
						projectList.add(pro);
					}
			}

			for( Project pro : this.projectRepository.findAll()) {
				if(pro.getCategory().name().toLowerCase().equalsIgnoreCase(keyword.toLowerCase())) {
					projectList.add(pro);
				}
			}

			for( Project pro : this.projectRepository.findAll()) {

				for(String member : pro.getGroupMember()) {
					if(member.toLowerCase().contains(keyword.toLowerCase())) {
						projectList.add(pro);
						break;
					}
				}


			}


		} catch (Exception e) {
			this.logger.warn(e);
		}

		return getUniqueProject(projectList);


	}

	public List<Project> getUniqueProject(final List<Project> projects) {
		return   projects.stream()
				.collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Project::getId))),
						ArrayList::new));

	}

}
