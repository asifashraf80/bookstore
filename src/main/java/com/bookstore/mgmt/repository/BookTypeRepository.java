/**
 * @Project : Library Management
 * FileName : BookTypeRepository.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.mgmt.domain.BookType;

/**
 * @author Sajid
 *
 */
@Repository
public interface BookTypeRepository extends PagingAndSortingRepository<BookType, Long>{

	/**
	 * Find BookType by Code
	 * @param code
	 * @return
	 */
	BookType findByCode(String code);	
}
