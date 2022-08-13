/**
 * @Project : Library Management
 * FileName : OrderItemDTO.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.dto;

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
public class OrderItemDTO extends BookDTO {

	private Double discountRate;
	private Double price;
	private Double discountedPrice;
	
}
