/**
 * @Project : Library Management
 * FileName : BookRepository.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.mgmt.domain.Book;

/**
 * @author Sajid
 *
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	/**
	 * Persist Book 
	 * @author Sajid	 * 
	 */
	Book save(Book book);

	/**
	 * Find BookType by ISBN
	 * @param isbn
	 * @return
	 */
	Book findByIsbnAndDeleted(String isbn , Boolean deleted);

	/**
	 * Find BookType by ISBN
	 * @param isbn
	 * @return
	 */
	Book findByIsbn(String isbn);
	
	/**
	 * Find current book by id
	 * @param id
	 * @param current
	 * @return
	 * @Author : Sajid
	 */
	Optional<Book> findByIdAndCurrent(Long id, Boolean current);

	/**
	 * Find BookType by ISBN
	 * @param isbn
	 * @return
	 */
	Book findByIsbnAndDeletedAndCurrent(String isbn , Boolean deleted,Boolean current);
}
