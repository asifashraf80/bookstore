/**
 * @Project : Library Management
 * FileName : BookServiceImplTest.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookstore.mgmt.domain.Book;
import com.bookstore.mgmt.domain.BookType;
import com.bookstore.mgmt.dto.BookRequest;
import com.bookstore.mgmt.dto.SearchBookRequest;
import com.bookstore.mgmt.repository.BookRepository;
import com.bookstore.mgmt.repository.BookTypeRepository;
import com.bookstore.mgmt.repository.OrderItemRepository;
import com.bookstore.mgmt.repository.OrderRepository;
import com.bookstore.mgmt.repository.PromotionRepository;
import com.bookstore.mgmt.service.impl.BookServiceImpl;

/**
 * @author Sajid
 *
 */
@SpringBootTest()
@RunWith(SpringRunner.class)
public class BookServiceImplTest {

	@TestSubject
	@Autowired
	private BookServiceImpl bookService;

	@MockBean
	private BookRepository bookRepository;
	@MockBean
	private BookTypeRepository bookTypeRepo;
	@MockBean
	private PromotionRepository promotionRepository;
	@MockBean
	private OrderRepository orderRepository;
	@MockBean
	private OrderItemRepository orderItemRepository;

	@Test
	public void testDeleteBook_AssertError() {
		Mockito.when(bookRepository.findByIsbnAndDeleted(anyString(), anyBoolean())).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> {
			bookService.deleteBook("123");
		});
	}

	@Test
	public void testDeleteBook() {
		Mockito.when(bookRepository.findByIsbnAndDeleted(anyString(), anyBoolean())).thenReturn(new Book());
		bookService.deleteBook("123");
	}
	
	@Test
	public void testSaveBook_AssertError() {
		Mockito.when(bookTypeRepo.findByCode(anyString())).thenReturn(new BookType());
		assertThrows(IllegalArgumentException.class, () -> {
			bookService.saveBook(new BookRequest());
		});
	}	

	@Test
	public void testSaveBook_Success() {
		BookType bt = new BookType();
		Mockito.when(bookTypeRepo.findByCode(anyString())).thenReturn(bt);
		Mockito.when(bookRepository.save(new Book())).thenReturn(new Book());
		
		
		BookRequest bookRequest = new BookRequest();
		bookRequest.setIsbn("ISBN1");
		bookRequest.setBookTypeCode("BT001");
		bookService.saveBook(bookRequest);
	}
	
	@Test
	public void testSearchBook() {
		
		List<Book> books = new ArrayList<>();
		BookType bt = new BookType();
		Mockito.when(bookRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(books));
		SearchBookRequest request = new SearchBookRequest();
		request.setPageNo(0L);
		request.setPageSize(1L);
		
		bookService.searchBook(request);
		
	}		
	
}
