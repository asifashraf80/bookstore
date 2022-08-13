/**
 * @Project : Library Management
 * FileName : PaginatedResults.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.dto;

import java.io.Serializable;
import java.util.List;

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
public class PaginatedResults<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7959497997592554256L;

	/** The results. */
	private List<T> results;

	/** The full list size. */
	private int totalElements;

	/** The objects per page. */
	private int objectsPerPage;

	/** The page number. */
	private int pageNumber;
	private int totalPages;

	
}
