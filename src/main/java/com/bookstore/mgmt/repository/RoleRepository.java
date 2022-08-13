/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.mgmt.common.Constants.RoleEnum;
import com.bookstore.mgmt.security.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> { 

	Optional<Role> findByRole(RoleEnum role);
}
