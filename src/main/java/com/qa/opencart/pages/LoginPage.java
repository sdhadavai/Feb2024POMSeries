package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Page Objects: By Locators

	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	// 2. public constructor of the page

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//public page actions/methods
	@Step("Getting login page title...")
	public String getLoginPageTitle() {
		String title =eleUtil.waitForTitleToBe(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME);
		System.out.println("login page title : " + title);
		return title;
	}
	@Step("Getting login page url...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME);
		System.out.println("login page URL : " + url);
		return url;
	}
	@Step("Getting the state of forgot password link exists...")
	public boolean checkForgotPwdLinkExixt() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
		//return eleUtil.isElementDisplayed(forgotPwdLink);
		
	}
	@Step("Login to application with username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("User credentials: " + username + "/" + pwd);
		eleUtil.doSendKeys(emailId, username, TimeUtil.DEFAULT_MEDIUM_TIME);
		eleUtil.doSendKeys(password,pwd);
		eleUtil.doClick(loginBtn);
				
		return new AccountsPage(driver);
		//return driver.getTitle();
	}
	@Step("Navigating to the register page...")
	public RegistrationPage navigteToRegistrationPage() {
		eleUtil.doClick(registerLink, TimeUtil.DEFAULT_TIME);
		return new RegistrationPage(driver);
	}
	
	

}
