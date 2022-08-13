/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.security.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bookstore.mgmt.security.model.UserDetailsImpl;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
	UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException;
	void createUser();
}
