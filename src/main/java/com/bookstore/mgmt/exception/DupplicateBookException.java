/**
 * @Project : Library Management
 * FileName : DupplicateBookException.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.exception;

/**
 * @author Sajid
 *
 */
public class DupplicateBookException extends RuntimeException{

	public DupplicateBookException(String message) {
		super(message);
	}	
}
