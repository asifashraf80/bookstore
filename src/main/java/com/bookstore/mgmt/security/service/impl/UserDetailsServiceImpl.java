/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.security.service.impl;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.mgmt.common.Constants.RoleEnum;
import com.bookstore.mgmt.repository.RoleRepository;
import com.bookstore.mgmt.repository.UserRepository;
import com.bookstore.mgmt.security.model.Role;
import com.bookstore.mgmt.security.model.User;
import com.bookstore.mgmt.security.model.UserDetailsImpl;
import com.bookstore.mgmt.security.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService  {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional
	public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}
	
	@Override
	@PostConstruct
	public void createUser() {
		Role role = new Role();
		role.setRole(RoleEnum.ROLE_USER);
		roleRepository.save(role);

			User user = new User();
			user.setUsername("sajid");
			user.setPassword(passwordEncoder.encode("test123"));
			user.getRoles().add(role);
			userRepository.save(user);
	}
}
