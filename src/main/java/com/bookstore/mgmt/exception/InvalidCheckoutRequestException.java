/**
 * @Project : Library Management
 * FileName : InvalidCheckoutRequestException.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.exception;

/**
 * @author Sajid
 *
 */
public class InvalidCheckoutRequestException extends RuntimeException{

	public InvalidCheckoutRequestException(String message) {
		super(message);
	}	
}
