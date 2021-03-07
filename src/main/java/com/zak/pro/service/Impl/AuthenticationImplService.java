package com.zak.pro.service.Impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zak.pro.dto.AuthenticationDTO;
import com.zak.pro.dto.ForgetPasswordResetDTO;
import com.zak.pro.dto.JwtDTO;
import com.zak.pro.dto.MailDTO;
import com.zak.pro.entity.Account;
import com.zak.pro.entity.Company;
import com.zak.pro.entity.Investor;
import com.zak.pro.entity.Student;
import com.zak.pro.entity.Totp;
import com.zak.pro.exception.CustomException;
import com.zak.pro.repository.AccountRepository;
import com.zak.pro.repository.TotpRepository;
import com.zak.pro.security.JWTProvider;
import com.zak.pro.service.AuthenticationService;
import com.zak.pro.service.MailService;
import com.zak.pro.util.PasswordUtil;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Service
public class AuthenticationImplService implements AuthenticationService {

	@Value("${security.jwt.secret}")
	private String secret;

	@Value("${security.jwt.expiration.time}")
	private long expirationTime;

	@Autowired
	private MailService mailService;

	@Autowired
	private AuthenticationManager authenticationManger;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TotpRepository totprepository;

	@Autowired
	private PasswordUtil passwordUtil;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private MessageSource messageSource;

	@Value("${spring.mail.username}")
	private String from;

	@Value("${spring.mail.sign}")
	private String sign;

	@Value("${spring.mail.location}")
	private String location;

	@Override
	public JwtDTO authenticateUser(AuthenticationDTO authenticationDTO) throws AuthenticationException, IOException {
		this.authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationDTO.getMobileOrEmail(), authenticationDTO.getPassword()));
		Account account = this.accountRepository.findByMobile(authenticationDTO.getMobileOrEmail());
		if (account == null) {
			account = this.accountRepository.findByEmail(authenticationDTO.getMobileOrEmail());
		}
		if (account != null) {
			String role = account.getRole().getRole();
			String firstName = "";
			String lastName = "";
			if (account instanceof Student) {
				firstName = ((Student) account).getFirstName();
				lastName = ((Student) account).getLastName();
			} else if (account instanceof Investor) {
				firstName = ((Investor) account).getFirstName();
				lastName = ((Investor) account).getLastName();
			} else {
				firstName = ((Company) account).getCompanyName();
			}
			String jwt = JWTProvider.generateJWT(firstName, lastName, account.getEmail(), account.getMobile(),
					account.isAcceptCGU(), role, this.secret, this.expirationTime);
			JwtDTO jwtDTO = new JwtDTO();
			jwtDTO.setJwt(jwt);
			if (account.getNbrConnection() > 0) {
				account.setFirstTime(false);
			}
			jwtDTO.setFirstTimeConnexion(account.isFirstTime());
			account.setNbrConnection(account.getNbrConnection() + 1);
			this.accountRepository.save(account);
			return jwtDTO;
		}
		JwtDTO jwtDTO = new JwtDTO();
		jwtDTO.setJwt("error");
		return jwtDTO;
	}

	@Override
	public String updateToken(String email) throws AuthenticationException, IOException {
		Account account = this.accountRepository.findByEmail(email);
		if (account != null) {
			String role = account.getRole().getRole();
			String firstName = "";
			String lastName = "";
			if (account instanceof Student) {
				firstName = ((Student) account).getFirstName();
				lastName = ((Student) account).getLastName();
			} else if (account instanceof Investor) {
				firstName = ((Investor) account).getFirstName();
				lastName = ((Investor) account).getLastName();
			} else {
				firstName = ((Company) account).getCompanyName();
			}
			String jwt = JWTProvider.generateJWT(firstName, lastName, account.getEmail(), account.getMobile(),
					account.isAcceptCGU(), role, this.secret, this.expirationTime);
			return jwt;
		}
		return "error";
	}

	@Transactional
	@Override
	public void saveTotp(String email) throws Exception {
		Account account = this.accountRepository.findByEmail(email);
		if (account == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		String totp = this.buildTotp();
		MailDTO mailDto = new MailDTO();
		mailDto.setFrom(this.from);
		mailDto.setTo(email);
		mailDto.setTemplate("resetPasswordDemande");
		mailDto.setSubject("Reset Password Verification");
		Map<String, Object> props = new HashMap<>();
		props.put("link", this.buildLink(email, totp));
		props.put("sign", this.sign);
		props.put("location", this.location);
		mailDto.setProps(props);
		this.mailService.sendMail(mailDto);
		Totp totpModel = new Totp();
		totpModel.setTotp(totp);
		totpModel.setValidity(LocalDateTime.now());
		totpModel = this.totprepository.save(totpModel);
		account.setTotp(totpModel);
		this.accountRepository.save(account);

	}

	private String buildTotp() {
		return UUID.randomUUID().toString().substring(0, 5);
	}

	private String buildLink(String email, String totp) {
		StringBuilder strbld = new StringBuilder(
				"https://investme.page.link/?link=https://play.google.com/store&apn=top.stores.investme&emailID=");
		strbld.append(email).append("&totp=").append(totp);
		return strbld.toString();
	}

	@Transactional
	@Override
	public void resetPassword(ForgetPasswordResetDTO passwordResetDTO) throws CustomException {
		Account account = this.accountRepository.findByEmail(passwordResetDTO.getEmail());
		if (account == null) {
			throw new CustomException(
					this.messageSource.getMessage("email.not.found", null, LocaleContextHolder.getLocale()));
		}
		Totp baseTotp = account.getTotp();
		if (baseTotp == null) {
			throw new CustomException(
					this.messageSource.getMessage("totp.not.found", null, LocaleContextHolder.getLocale()));

		}
		if (LocalDateTime.now().isAfter(baseTotp.getValidity().plusMinutes(30))) {
			throw new AccessDeniedException(
					this.messageSource.getMessage("totp.expired", null, LocaleContextHolder.getLocale()));

		}
		if (!this.passwordUtil.isPasswordValid(passwordResetDTO.getPassword())) {
			throw new CustomException(
					this.messageSource.getMessage("password.not.compliant", null, LocaleContextHolder.getLocale()));

		}
		if (!baseTotp.getTotp().equals(passwordResetDTO.getTotp())) {
			throw new AccessDeniedException(
					this.messageSource.getMessage("totp.invalid", null, LocaleContextHolder.getLocale()));

		}
		account.setPassword(this.bcrypt.encode(passwordResetDTO.getPassword()));
		account.setTotp(null);
		this.accountRepository.save(account);
		this.totprepository.delete(baseTotp);
	}

}
