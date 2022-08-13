/**
 * @Project : Library Management
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO implements Serializable{
	private Long id;
	private BookTypeDTO bookType;
	private String name;
	private String description;
	private String author;	
	private Double price;
	private String isbn;

}
