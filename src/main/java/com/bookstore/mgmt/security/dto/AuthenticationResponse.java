/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.security.dto;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8779025570935699980L;
	private String authToken;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	

}
