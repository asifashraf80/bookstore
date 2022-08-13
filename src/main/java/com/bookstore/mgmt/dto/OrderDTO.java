/**
 * @Project : Library Management
 * FileName : OrderDTO.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
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
public class OrderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3817496519507803147L;
	private String orderReference;
	private List<OrderItemDTO> items = new ArrayList<OrderItemDTO>();
	private Double totalAmount;
	private String promotionCode;
	
}
