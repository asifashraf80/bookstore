/**
 * @Project : Library Management
 * FileName : BookNotFoundException.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.exception;

/**
 * @author Sajid
 *
 */
public class BookNotFoundException extends RuntimeException{

	public BookNotFoundException(String message) {
		super(message);
	}	
}
