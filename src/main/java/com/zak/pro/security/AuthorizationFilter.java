package com.zak.pro.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zak.pro.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

	@Value("${security.jwt.secret}")
	private String secret;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!JWTProvider.ifOption(request, response)) {
			try {
				String jwt = JWTProvider.resolveJWT(request);
				if (!jwt.equals("")) {
					if (JWTProvider.validateJwt(jwt, this.secret)) {
						this.authUser(jwt);
					}
				}
			} catch (SignatureException e) {
				AuthorizationFilter.LOGGER.error("", e);
			} catch (MalformedJwtException e) {
				AuthorizationFilter.LOGGER.error("", e);
			} catch (ExpiredJwtException e) {
				AuthorizationFilter.LOGGER.error("", e);
			} catch (CustomException e) {
				AuthorizationFilter.LOGGER.error("", e);
			}

		} else {
			response = JWTProvider.addAuthorizedHeaders(response);
		}
		filterChain.doFilter(request, response);
	}

	private void authUser(String jwt) {
		Jws<Claims> parsedJwt = JWTProvider.parseJwt(jwt, this.secret);
		String username = (String) parsedJwt.getBody().get("email");
		String role = (String) parsedJwt.getBody().get("role");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
	}

}
