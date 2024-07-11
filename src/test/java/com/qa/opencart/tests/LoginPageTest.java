package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Open Cart Application with Shopping Workflow...")
@Story("US101: Design Logn page for Open Cart Application")
@Feature("F50: Feature login page")
//@Listeners({ExtentReportListener.class, TestAllureListener.class, AnnotationTransformer.class})
//AnnotationTransformer.class is not working from individual page. Only report will work not retry
public class LoginPageTest extends BaseTest {
	
	@Description("Checking login page title test----")
	@Severity(SeverityLevel.MINOR)
	@Owner("Srinvas Dhadavai")
	@Issue("Login-123")
	@Feature("Login Page Title feature")
	@Test(priority =1)
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
		
	}
	
	@Description("Checking login page url ----")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority =2)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
		
	}
	@Description("Checking login page forgot pwd link exists on the login page ----")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority =3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.checkForgotPwdLinkExixt(), AppError.ELEMENT_NOT_FOUND);
		
	}
	@Description("Checking user is able to login to Open Cart app suscessfully ----")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority =4)
	public void loginTest() {
		//accountsPage = loginPage.doLogin(prop.getProperty("username"), System.getProperty("password"));//Use this if u r passing password from cmd we want to make sure wherever loginTest() is called otherwise you will get nulls
		
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));//use this id u r passing password from properties file
		Assert.assertEquals(accountsPage.getAccountPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	

}
