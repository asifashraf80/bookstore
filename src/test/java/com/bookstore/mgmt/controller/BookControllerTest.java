/**
 * @Project : Library Management
 * FileName : BookControllerTest.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.controller;

import static org.mockito.ArgumentMatchers.anyString;

import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookstore.mgmt.dto.BookDTO;
import com.bookstore.mgmt.dto.BookRequest;
import com.bookstore.mgmt.dto.CheckoutRequest;
import com.bookstore.mgmt.dto.OrderDTO;
import com.bookstore.mgmt.dto.PaginatedResults;
import com.bookstore.mgmt.dto.Response;
import com.bookstore.mgmt.dto.SearchBookRequest;
import com.bookstore.mgmt.dto.SearchBookResponse;
import com.bookstore.mgmt.service.impl.BookServiceImpl;

import junit.framework.Assert;

/**
 * @author Sajid
 *
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
public class BookControllerTest {

	@TestSubject
	@Autowired
	private BookController bookController;
	
	
	@MockBean
	private BookServiceImpl bookService;
	
	@Test
	public void deleteBookTest() {
		Mockito.when(bookService.deleteBook(anyString())).thenReturn(null);
		bookController.delete("123");
	}	
	
	@Test
	public void newBookRequestTest() {		
		BookRequest request = new BookRequest();
		request.setIsbn("123");
		request.setBookTypeCode("BT001");		
		BookDTO response = new BookDTO();		
		Mockito.when(bookService.saveBook(request)).thenReturn(response);		
		
		Response<BookDTO> responseEntity = bookController.newBookRequest(request);
		Assert.assertEquals("200", responseEntity.getCode());
		
	}
	
	@Test
	public void updateBookRequestTest() {		
		BookRequest request = new BookRequest();
		request.setIsbn("123");
		request.setBookTypeCode("BT001");
		request.setId(1L);
		BookDTO response = new BookDTO();		
		Mockito.when(bookService.saveBook(request)).thenReturn(response);
		Response<BookDTO> responseEntity = bookController.updateBookRequest(request);
		Assert.assertEquals("200", responseEntity.getCode());
	}
	
	@Test
	public void searchBookTest() {
		SearchBookRequest request = new SearchBookRequest();
		PaginatedResults<BookDTO> response = new PaginatedResults<BookDTO>();
		Mockito.when(bookService.searchBook(request)).thenReturn(response);
		
		Response<PaginatedResults<BookDTO>> responseEntity = bookController.searchBook(request);
		Assert.assertEquals("200", responseEntity.getCode());
	}
	
	@Test
	public void checkoutBookTest() {
		CheckoutRequest request = new CheckoutRequest();
		OrderDTO response = new OrderDTO();
		Mockito.when(bookService.checkout(request)).thenReturn(response);		
		Response<OrderDTO> responseEntity = bookController.checkout(request);
		Assert.assertEquals("200", responseEntity.getCode());
	}
	
}
