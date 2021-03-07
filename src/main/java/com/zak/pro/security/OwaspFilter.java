package com.zak.pro.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
//@WebFilter(urlPatterns = "/*")
//@Component
public class OwaspFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		this.addOwaspSecurityHeaders(response);
		chain.doFilter(new OwaspRequestWrapper((HttpServletRequest) request), response);
	}

	private void addOwaspSecurityHeaders(ServletResponse response) {
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("STRICT-TRANSPORT-SECURITY", "max-age=31536000; includeSubDomains");
		// X-Frame-Options (ClickJacking)
		res.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		// X-XSS-Protection (Cross-site Scripting)
		res.addHeader("X-XSS-PROTECTION", "1; mode=block");
		// X-Content-Type-Options
		res.addHeader("X-CONTENT-TYPE-OPTIONS", "nosniff");
	}

}
