package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By logoutLink = By.linkText("Logout");
	private By hraders = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	public String getAccountPageTitle() {
		String title  = eleUtil.waitForTitleToBe(AppConstants.ACCOUNT_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Account page title : " + title);
		return title;
	}

	public String getAccountPageURL() {
		eleUtil.waitForURLContains(AppConstants.ACCOUNT_PAGE_FRACTION_URL, TimeUtil.DEFAULT_MEDIUM_TIME);
		String url = driver.getCurrentUrl();
		System.out.println("Account page URL : " + url);
		return url;
	}

	public boolean isLogoutLinkExists() {
		return eleUtil.doIsDisplayed(logoutLink);
		
	}

	public List<String> getAccPageHeaders() {
		
		List<WebElement> headersList = eleUtil.waitForPresenceOfElementsLocated(hraders, TimeUtil.DEFAULT_MEDIUM_TIME);
		List<String> headerValueList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headerValueList.add(text);
		}
		return headerValueList;
	}

	public boolean isSearchExist() {
		return eleUtil.doIsDisplayed(search);
		

	}

	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("Searching : " + searchKey);
		if (isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchResultsPage(driver);
		}else {
			System.out.println("Search field not present on the page");
			return null;
		}
	}

}
