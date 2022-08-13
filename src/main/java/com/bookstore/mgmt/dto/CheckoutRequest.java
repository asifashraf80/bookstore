/**
 * @Project : Library Management
 * FileName : CheckoutRequest.java
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutRequest implements Serializable{
	private List<Long> bookIds;
	private String promocode;
}
