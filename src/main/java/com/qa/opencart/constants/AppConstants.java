package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class AppConstants {
	
	public static final String CONFIG_FILE_PATH = "./src/test/resources/config/config.properties";
	public static final String CONFIG_QA_FILE_PATH = "./src/test/resources/config/qa.properties";
	public static final String CONFIG_DEV_FILE_PATH = "./src/test/resources/config/dev.properties";
	public static final String CONFIG_UAT_FILE_PATH = "./src/test/resources/config/uat.properties";
	public static final String CONFIG_STAGE_FILE_PATH = "./src/test/resources/config/stage.properties";
	
	public static final String LOGIN_PAGE_TITLE ="Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL ="route=account/login";
	public static final String ACCOUNT_PAGE_TITLE ="My Account";
	public static final String ACCOUNT_PAGE_FRACTION_URL ="route=account/account";
	public static final List<String> ACC_PAGE_HEADER_LIST = Arrays.asList("My Account", "My Orders", "My Affiliate Account","Newsletter");
	public static final String USER_REGISTER_SUCCESS_MESSG = "Your Account Has Been Created";
	
	//************************ SHEET CONSTANTS **************//
	public static final String REGISTER_SHEET_NAME = "register";
	public static final String LOGIN_SHEET_NAME = "login";
	public static final String PRODCUT_SHEET_NAME = "products";
	public static final String PRODCUT_IMAGES_SHEET_NAME ="productimages";
	
	

}
