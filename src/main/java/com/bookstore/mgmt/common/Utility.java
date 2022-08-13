/**
 * @Project : Library Management
 * FileName : Utility.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.common;

import java.util.Collection;

public class Utility {

	/**
	 * Check String is empty or null
	 * @param value
	 * @author Sajid
	 * @return
	 */
	public static Boolean isNullOrEmpty(String value) {
		return value== null || value.length() == 0;
	}
	
	public static Boolean isNullOrEmpty(Collection collection) {
		return null == collection || collection.size() ==0;
	}
	
	public static class OrderReference{
	    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    private static final String lower = upper.toLowerCase();
	    private static final String digits = "0123456789";
	    private static final String alphanum = upper + lower + digits;
	    private static final int ORDER_REF_LENGTH=12;
	    public static final String generateOrderReference() {
	    	StringBuilder orderReference = new StringBuilder();
	    	int index = -1;
	    	while(orderReference.length() < ORDER_REF_LENGTH) {
	    		index = (int)(alphanum.length()*Math.random());
	    		orderReference.append(alphanum.charAt(index));
	    	}	    	
	    	return orderReference.toString();
	    }
	    
	}
}
