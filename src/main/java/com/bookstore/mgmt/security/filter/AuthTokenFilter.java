/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookstore.mgmt.common.Utility;
import com.bookstore.mgmt.security.model.UserDetailsImpl;
import com.bookstore.mgmt.security.service.UserDetailsService;
import com.bookstore.mgmt.security.util.AuthTokenUtil;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final String BEARER = "Bearer ";
	private static final Logger log = LogManager.getLogger(AuthTokenFilter.class);
	
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthTokenUtil authTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestTokenHeader = request.getHeader("Authorization");
		String userName = null;
		String jwtToken = null;
		if(null != requestTokenHeader && requestTokenHeader.startsWith(BEARER)) {
			jwtToken = requestTokenHeader.substring(BEARER.length());			
			try {
				logger.debug("Getting Username from Token");
				userName = authTokenUtil.getUsernameFromToken(jwtToken);
			}catch (Exception e) {
				log.error(e);
			}
		}
		
		if(!Utility.isNullOrEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetailsImpl userDetails = userDetailService.loadUserByUsername(userName);
			if(authTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// 	that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);				
			}			
		}	
		filterChain.doFilter(request, response);
	}
}
