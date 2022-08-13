/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.security.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint{
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");		
	}
}
