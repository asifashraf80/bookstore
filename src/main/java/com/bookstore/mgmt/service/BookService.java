/**
 * @Project : Library Management
 * FileName : BookService.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.service;

import com.bookstore.mgmt.dto.BookDTO;
import com.bookstore.mgmt.dto.BookRequest;
import com.bookstore.mgmt.dto.CheckoutRequest;
import com.bookstore.mgmt.dto.OrderDTO;
import com.bookstore.mgmt.dto.PaginatedResults;
import com.bookstore.mgmt.dto.SearchBookRequest;

/**
 * @author Sajid
 *
 */
public interface BookService {

	/**
	 * Save Book 
	 * @author Sajid
	 * @param request
	 */
	BookDTO saveBook(BookRequest request);
	
	/**
	 * Search Books by Criteria
	 * @author Sajid
	 * @return
	 */
	 PaginatedResults<BookDTO> searchBook(SearchBookRequest request);
	
	 /**
	  * Delete Book
	  * @param request
	  * @Author : Sajid
	 * @return TODO
	  */
	 String deleteBook(String isbn);
	 
	 /**
	  * Checkout Books
	  * @param request
	  * @Author : Sajid
	 * @return TODO
	  */
	 OrderDTO checkout(CheckoutRequest request);
	
}
