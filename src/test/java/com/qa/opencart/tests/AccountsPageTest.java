package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;

public class AccountsPageTest extends BaseTest{

	@BeforeClass
	public void accSetUp() {
		accountsPage =loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accountPageTitleTest() {
		Assert.assertEquals(accountsPage.getAccountPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Test
	public void accountPageURLTest() {
		Assert.assertTrue(accountsPage.getAccountPageURL().contains(AppConstants.ACCOUNT_PAGE_FRACTION_URL), AppError.URL_NOT_FOUND);
	}
	public void accountPageHeadersText() {
		List<String> accPageHeadersList =accountsPage.getAccPageHeaders();
		Assert.assertEquals(accPageHeadersList, AppConstants.ACC_PAGE_HEADER_LIST, AppError.LIST_NOT_MATCHED);
		
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"Airtel", 0}
		};
	}
	
	
	@Test(dataProvider="getSearchData")
	public void searchTest(String serachKey, int resultCount) {
		searchResultsPage = accountsPage.doSearch(serachKey);
		Assert.assertEquals(searchResultsPage.getSearchResultCount(), resultCount, AppError.RESULT_COUNT_MISMATCHED);
		}
}
