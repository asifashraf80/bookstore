/**
 * @Project : Library Management
 * FileName : BookController.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.controller;

import static com.bookstore.mgmt.common.Constants.ResponseCode.FAILURE;
import static com.bookstore.mgmt.common.Constants.ResponseCode.SUCCESS;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.mgmt.dto.BookDTO;
import com.bookstore.mgmt.dto.BookRequest;
import com.bookstore.mgmt.dto.CheckoutRequest;
import com.bookstore.mgmt.dto.OrderDTO;
import com.bookstore.mgmt.dto.PaginatedResults;
import com.bookstore.mgmt.dto.Response;
import com.bookstore.mgmt.dto.SearchBookRequest;
import com.bookstore.mgmt.service.BookService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

/**
 * @author Sajid
 *
 */
@RestController
@RequestMapping("/api/book")
@Log4j2
public class BookController {

	@Autowired
	private BookService bookService;

	@Value("${bookstore.msg.invalid_new_request}")
	private String invalidNewBookMessage;
	@Value("${bookstore.msg.invalid_update_request}")
	private String invalidUpdateBookMessage;
	
	/**
	 * The Logger
	 */
	private static final Logger logger = LogManager.getLogger(BookController.class);

	/**
	 * Process new Book request
	 * @param request
	 * @return
	 * @Author : Sajid
	 */
	@PostMapping("/")
	@ApiOperation(value = "Process a new Book request", notes = "Returns saved book details")
	@ApiResponses(value = {
			 @ApiResponse(code = 200, message = "Successfully Saved New Book"),
			 @ApiResponse(code = 1001, message = "Some Error Occured")
	})		
	public Response<BookDTO> newBookRequest(@RequestBody BookRequest request){
		Response<BookDTO> response = null;
		log.info("Processing New Book Request....");
		if(null != request.getId()) {
			response = new Response<BookDTO>();
			log.error("New Book request should not have ID");
			response.setCode(FAILURE.getCode());
			response.setMessage(invalidNewBookMessage);
		}else {
			response = saveBook(request);
		}
		
		return response;
	}
	
	/**
	 * Process Update Book Request
	 * @param request
	 * @return
	 * @Author : Sajid
	 */
	@PutMapping("/")
	public Response<BookDTO> updateBookRequest(@RequestBody BookRequest request){
		log.info("Processing Update Book Request....");
		Response<BookDTO> response = null;
		if(null == request.getId()) {
			response = new Response<BookDTO>();
			log.error("Update Book request should have ID");
			response.setCode(FAILURE.getCode());
			response.setMessage(invalidUpdateBookMessage);
		}else {
			response = saveBook(request);
		}
		
		return response;
	}
	
	/**
	 * Save Book
	 * @param request
	 * @return
	 * @Author : Sajid
	 */
	private Response<BookDTO> saveBook(@RequestBody BookRequest request) {
		logger.info("Create Book Service is called...");
		Response<BookDTO> response = new Response<BookDTO>();
		try {			
			response.setCode(SUCCESS.getCode());
			response.setContent(bookService.saveBook(request));
		} catch (Exception e) {
			log.error("Caught Exception while search Book", e);
			response.setCode(FAILURE.getCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * search books by given filters
	 * @param request
	 * @return
	 * @Author : Sajid
	 */
	@PostMapping("/search")
	public Response<PaginatedResults<BookDTO>> searchBook(@RequestBody SearchBookRequest request) {
		Response<PaginatedResults<BookDTO>> response = new Response<PaginatedResults<BookDTO>>();
		try {
			PaginatedResults<BookDTO> searchResult = bookService.searchBook(request);
			response.setCode(SUCCESS.getCode());
			response.setContent(searchResult);
		} catch (Exception e) {
			log.error("Caught Exception while search Book", e);
			response.setCode(FAILURE.getCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * Delete Book
	 * @param isbn
	 * @return
	 * @Author : Sajid
	 */
	@DeleteMapping("/{isbn}")
	public Response<String> delete(@PathVariable String isbn) {
		Response<String> response = new Response<String>();
		try {
			bookService.deleteBook(isbn);
			response.setCode(SUCCESS.getCode());			
		} catch (Exception e) {
			log.error("Caught Exception while search Book", e);
			response.setCode(FAILURE.getCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Checkout Book
	 * @param checkoutRequest
	 * @return
	 * @Author : Sajid
	 */
	@PostMapping("/checkout")
	public Response<OrderDTO> checkout(@RequestBody CheckoutRequest checkoutRequest){
		Response<OrderDTO> response = new Response<OrderDTO>();
		try {
			OrderDTO order = bookService.checkout(checkoutRequest);
			response.setContent(order);
			response.setCode(SUCCESS.getCode());			
		} catch (Exception e) {
			log.error("Caught Exception while checking out Books", e);
			response.setCode(FAILURE.getCode());
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	
}
