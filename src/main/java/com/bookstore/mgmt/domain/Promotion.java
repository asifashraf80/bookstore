/**
 * @Project : Library Management
 * FileName : Promotion.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Promotion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3657612883390005657L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion", cascade = CascadeType.ALL)
	private Set<BookTypePromotion> promotions;
		
}
