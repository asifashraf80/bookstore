/**
 * @Project : Library Management
 * FileName : Constants.java
 * @Author : Sajid
 * This File and its contents are Property of SajidKhan - 2022
 */
package com.bookstore.mgmt.common;

public class Constants {

	
	
	
	public enum RoleEnum {
		ROLE_USER,
		ROLE_ADMIN
	}
	
	public enum ResponseCode{
		SUCCESS("200"),
		FAILURE("1001");
		private String code;
		private ResponseCode(String code) {
			this.code = code;			
		}
		
		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}
		
	}
	
	
	public static final Long ZERO = 0l;
	public static final Long ONE = 1l;
	
}
