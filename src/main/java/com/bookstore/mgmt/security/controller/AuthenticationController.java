/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.security.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.mgmt.security.dto.AuthenticationRequest;
import com.bookstore.mgmt.security.dto.AuthenticationResponse;
import com.bookstore.mgmt.security.model.UserDetailsImpl;
import com.bookstore.mgmt.security.service.UserDetailsService;
import com.bookstore.mgmt.security.util.AuthTokenUtil;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthTokenUtil tokenUtil;

	@Autowired
	private UserDetailsService userDetailService;

	
	
	@PostMapping(value = "/authenticate")
	public AuthenticationResponse generateAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception{
		authenticate(request.getUsername(), request.getPassword());
		UserDetailsImpl userDetails = userDetailService.loadUserByUsername(request.getUsername());
		String token = tokenUtil.generateToken(userDetails);
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthToken(token);
		return response;
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	

	
}
