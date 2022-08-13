/**
 * @Project : Library Management
 * FileName : BaseSearchRequest.java
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
@NoArgsConstructor
@AllArgsConstructor
public class BaseSearchRequest implements Serializable {	
	
	private List<Filter> filters;
	private Long pageNo;
	private Long pageSize;	
	
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Filter {
	    private String value;
	    private String id;	    
	}
	
	
}
