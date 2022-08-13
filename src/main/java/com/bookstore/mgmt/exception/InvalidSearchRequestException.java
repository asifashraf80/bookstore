/**
 * @Project : Library Management
 * FileName : InvalidSearchRequestException.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.exception;

/**
 * @author Sajid
 *
 */
public class InvalidSearchRequestException extends RuntimeException{

	public InvalidSearchRequestException(String message) {
		super(message);
	}

}
