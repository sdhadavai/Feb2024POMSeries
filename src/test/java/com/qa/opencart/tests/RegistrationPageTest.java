package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPageTest extends BaseTest{
	
	@BeforeClass
	public void regSetUp() {
		registrationPage = loginPage.navigteToRegistrationPage();
	}
	
	
	@DataProvider
	public Object[][] userRegTestData(){
		return new Object[][] {
			{"SSri", "Autmation","3435553452", "ss@123", "yes"},
			{"sai", "Autmation","3456787654", "ss@123", "no"},
			{"soham", "Autmation","5678908765", "ss@123", "yes"}
		};
		
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromSheet() {
		 return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		
	}
	
	@DataProvider
	public Object[][] userRegTestDataFromCSV() {
		 return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
		
	}
	
	@Test(dataProvider ="userRegTestDataFromSheet")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		
	Assert.assertTrue
	(registrationPage.userRegister(firstName,lastName, StringUtils.getRandomEmailId(), telephone, password, subscribe),
			AppError.USER_REG_NOT_DONE);
		
	}
	
	

}
