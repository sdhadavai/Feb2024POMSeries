package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void productInfoPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"macbook", "MacBook Pro"},
			{"imac", "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
			{"canon", "Canon EOS 5D"}
		};
	}
	
	@DataProvider
	public Object[][] getProductSheetData() {
		return ExcelUtil.getTestData(AppConstants.PRODCUT_SHEET_NAME);
			
	}
	
	@Test(dataProvider="getProductSheetData")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accountsPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName, AppError.HEADER_NOT_FOUND);
	}
	
	@DataProvider
	public Object[][] getProductIamgeData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7},
			{"canon", "Canon EOS 5D", 3}
		};
	}
	
	@DataProvider
	public Object[][] getProductIamgeSheetData() {
		return ExcelUtil.getTestData(AppConstants.PRODCUT_IMAGES_SHEET_NAME);
	}
	
	@Test(dataProvider="getProductIamgeSheetData")
	public void productImagesCountTest(String searchKey, String productName, String imagesCount) {
		searchResultsPage = accountsPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), Integer.parseInt(imagesCount), AppError.IMAGES_COUNT_MISMATCHED);
	}
	
	//test with multiple assertions(soft)
	@Test
	public void productInfoTest() {
		searchResultsPage = accountsPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap =productInfoPage.getProductInforMap();
		System.out.println("========== Product Information ==========");
		System.out.println(productInfoMap);
		
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("productPrice"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");
		softAssert.assertAll();//Failure info
		System.out.println("test is done");//it will not print if anyone assert is failed
	}
	
	//hard assert(Assert -static methods) If one assertion failed then it will stop there and will not move further in that @Test
	//vs 
	//soft assert(verify - SoftAssert - non static methods): If one assertion failed then it will move to next assertion
	
	//single assertion ==> use hard assertion
	//multiple assertion ==> use soft assertion
	
	//act vs exp: Assert.assertEquals(act, exp)
	//Assert.assertTrue(true)
	//Assert.assertTrue(title.contains("Google"))
	//Assert.assertFalse(false)

}
