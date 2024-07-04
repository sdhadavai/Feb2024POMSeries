package com.qa.opencart.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserException;

public class BrowserUtil {
	
	WebDriver driver;
	
	public WebDriver launchBrowser(String browserName) {
		System.out.println("The browser name is: " + browserName);
		
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "safari":
			driver = new SafariDriver();
			break;

		default:
			System.out.println("Invalid Browser passed... " + browserName);
			throw new BrowserException("Invalid Browser... " + browserName);
					
		}
		
		return driver;
		
	}
	
	public void launchURL(String url) {
		
		if(url==null) {
			throw new BrowserException("Invalid URL... " + url);
			
		}
		if(url.isBlank() || url.isEmpty()) {
			throw new BrowserException("Blank/Empty URL: " + url);
					
		}
		
		if(url.indexOf("http")>0 || url.indexOf("http")==-1) {
			throw new RuntimeException("HTTP(s) is missing... " + url);
			//throw new BrowserException("HTTP(s) is missing..." + url);
		}
		
		driver.get(url.trim());
	}
	 
	public String getPageTitle() {
		String title = driver.getTitle();
		if(title==null) {
			System.out.println("Getting null title");
			return null;
		}else {
			return title;
			
		}
	}
	
	public String getPageCurrentURL() {
		return driver.getCurrentUrl();
	}
	
	public void quitBrowser() {
		driver.quit();
	}

	public void closeBrowser() {
		driver.close();
	}
}
