/**
 * @Project : Library Management
 * FileName : Book.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sajid
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4684795286917317L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private BookType bookType;
	
	private String name;
	private String description;
	private String author;	
	private Double price;
	private String isbn;
	
	private Boolean deleted;
	private Boolean current;
}
