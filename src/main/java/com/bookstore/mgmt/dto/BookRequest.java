/**
 * @Project : Library Management
 * FileName : BookRequest.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sajid
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5461564528357129002L;
	private Long id;
	private String bookTypeCode;	
	private String name;
	private String description;
	private String author;	
	private Double price;
	private String isbn;
		
}
