/**
 * @Project : Library Management
 * FileName : PromotionRepository.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.mgmt.domain.Promotion;

/**
 * @author Sajid
 *
 */
@Repository
public interface PromotionRepository extends PagingAndSortingRepository<Promotion, Long>, JpaSpecificationExecutor<Promotion> {

	/**
	 * Search Promotion By Code
	 * @param code
	 * @return
	 * @Author : Sajid
	 */
	Promotion findByCode(String code);
}
