package com.qa.opencart.utils;

public class StringUtils {
	
	public static String getRandomEmailId() {
		
		String emailId = "userauto" + System.currentTimeMillis() + "@opencart.com";
		System.out.println("User Email Id: " + emailId);
		return emailId;
	}
	
	//delete user from user where emailId like '%userauto%'

}
