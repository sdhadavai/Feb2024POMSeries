package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productImagesCount = By.cssSelector("div#content a.thumbnail");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");	
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private By qtyField = By.cssSelector("input-quantity");
	private By addToCartBtn = By.cssSelector("button#button-cart");
	
	private Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String header = eleUtil.doGetText(productHeader);
		System.out.println("Product Header === " + header);
		return header;
	}
	
	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForVisiblityOfElementsLocated(productImagesCount, TimeUtil.DEFAULT_MEDIUM_TIME).size();
		System.out.println("Total no of Iamges Count: " + imagesCount);
		return imagesCount;
	}
	
	public Map<String, String> getProductInforMap() {
		productMap = new HashMap<String, String>();
		//productMap = new LinkedHashMap<String, String>();//Insertion order
		//productMap = new TreeMap<String, String>();//Sorted Order
		productMap.put("productname", getProductHeader());
		productMap.put("productimagescount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();
		return productMap;
		
		
	}
	
	/*
	 * Brand: Apple 
	 * Product Code: Product 18 
	 * Reward Points: 800 
	 * Availability: In Stock
	 */
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForVisiblityOfElementsLocated(productMetaData, TimeUtil.DEFAULT_MEDIUM_TIME);
		//System.out.println(metaList.size());
		for(WebElement e : metaList) {
			String metaData = e.getText();
			String[] meta = metaData.split(":");
			String metakey = meta[0].trim();
			//System.out.println("Key is: " + metakey);
			String metaValue = meta[1].trim();
			//System.out.println("Value is: " + metaValue);
			productMap.put(metakey, metaValue);
		}
		
	}
	
	//$2,000.00
	//Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String productPrice = priceList.get(0).getText();
		//System.out.println(productPrice);
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
		//System.out.println(exTaxPrice);
		productMap.put("productPrice", productPrice);
		productMap.put("exTaxPrice", exTaxPrice);
		
	}
	
	
	//Assignment enter quanity and go to arat page
	public void enterQty() {
		eleUtil.doSendKeys(qtyField, "2", TimeUtil.DEFAULT_MEDIUM_TIME);
	}
	
	

}
