package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	@Step("Finding the element using locator {0}")
	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			highlightElement(element);
			
			return element;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page..." + locator);
			e.printStackTrace();
			return null;
		}
	}

	private void nullCheck(String value) {
		if (value == null) {
			throw new ElementException("Value is null " + value);
		}
	}

	private void highlightElement(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}

	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		// WebElement element =getElement(locator);
		// element.sendKeys(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);

	}

	@Step("Entering value {1} in text field using locator: {0} and waiting for element with: {2} secs")
	public void doSendKeys(By locator, String value, int timeout) {
		nullCheck(value);
		waitForElementVisible(locator, timeout).clear();
		waitForElementVisible(locator, timeout).sendKeys(value);

	}

	@Step("Entering value {1} in text field using locator: {0}")
	public void doSendKeys(By locator, CharSequence value) {
		getElement(locator).sendKeys(value);

	}

	@Step("Clicking on element using loctor {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(By locator, int timeout) {
		waitForElementVisible(locator, timeout).click();

	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	@Step("Checking the state of locator: {0} is displayed or not")
	public boolean doIsDisplayed(By locator) {
		try {
			boolean flag = getElement(locator).isDisplayed();
			System.out.println("Element is displayed: " + locator);
			return flag;
		} catch (NoSuchElementException e) {
			System.out.println("Element with locator " + locator + " is not displayed");
			return false;
		}
	}

	public boolean isElementDisplayed(By locator) {
		int elementCount = getElements(locator).size();
		if (elementCount == 1) {
			System.out.println("Single element is displayed: " + locator);
			return true;
		} else {
			System.out.println("Multiple or zero elements are displayed: " + locator);
			return false;
		}
	}

	public boolean isElementDisplayed(By locator, int expectedElementCount) {
		int elementCount = getElements(locator).size();
		if (elementCount == expectedElementCount) {
			System.out.println("Element is displayed: " + locator + " with the occurance of " + expectedElementCount);
			return true;
		} else {
			System.out.println("Multiple or zero elements are displayed" + locator + " with the occurance of "
					+ expectedElementCount);
			return false;
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElementTextList(By locator) {

		List<WebElement> elementList = getElements(locator);
		List<String> elementTextList = new ArrayList<>();
		for (WebElement e : elementList) {
			String text = e.getText();
			if (text.length() != 0) {
				elementTextList.add(text);
			}
		}
		return elementTextList;
	}

	public List<String> getElementAttributeList(By locator, String attrName) {
		List<WebElement> imagesList = getElements(locator);
		List<String> attrList = new ArrayList<String>();
		for (WebElement e : imagesList) {
			String attrValue = e.getAttribute(attrName);
			if (attrValue != null && attrValue.length() != 0) {
				attrList.add(attrValue);
				System.out.println(attrValue);
			}
		}
		return attrList;

	}

	// ************************ Select drop down utils *********************
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(driver.findElement(locator));
		return select.getOptions().size();
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(driver.findElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void selectValueFromDropDown(By locator, String optionText) {
		Select select = new Select(getElement(locator));
		List<WebElement> options = select.getOptions();
		for (WebElement e : options) {
			String text = e.getText();
			if (text.equals(optionText.trim())) {
				e.click();
				break;
			}
		}

	}

	public void selectValueFromDropDownWithoutSelectClass(By locator, String optionText) {

		List<WebElement> optionsList = getElements(locator);
		System.out.println(optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(optionText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchField, String searchKey, By suggestions, String value) throws InterruptedException {
		doSendKeys(searchField, searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	public void deselectAllOptions(By locator) {
		WebElement element = getElement(locator);
		Select select = new Select(element);
		if (select.isMultiple()) {

			select.deselectAll();
		} else {
			System.out.println("Selected drop down is not a multi select option");
		}

	}

	// *******************Actions Utils************************************
	public void handleParentSubMenu(By parentLocator, By childLocator) throws InterruptedException {

		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).perform();
		Thread.sleep(2000);
		doClick(childLocator);

	}

	public void doDragAndDrop(By sourceLocator, By tagetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(tagetLocator)).perform();

	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();

	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).perform();

	}

	/**
	 * This method is used to enter the value in the text field with pause
	 * 
	 * @param locator
	 * @param value
	 * @param pauseTime
	 */
	public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char[] chArray = value.toCharArray();
		for (char c : chArray) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(pauseTime).perform();
		}
	}

	/**
	 * This method is used to enter the value in the text field with pause of 500 ms
	 * (By default)
	 * 
	 * @param locator
	 * @param value
	 */
	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char[] chArray = value.toCharArray();
		for (char c : chArray) {
			act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).perform();
		}
	}

	public void level4MenuSubmenuHandlingUsingClick(By level1, String level2, String level3, String level4)
			throws InterruptedException {
		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(By.linkText(level2))).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(By.linkText(level3))).perform();
		Thread.sleep(1000);
		doClick(By.linkText(level4));
	}

	public void level4MenuSubmenuHandlingUsingClick(By level1, By level2, By level3, By level4)
			throws InterruptedException {
		doClick(level1);
		Thread.sleep(1000);

		Actions act = new Actions(driver);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	public void level4MenuSubmenuHandlingUsingMouseHover(By level1, By level2, By level3, By level4)
			throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level2)).perform();
		Thread.sleep(1000);
		act.moveToElement(getElement(level3)).perform();
		Thread.sleep(1000);
		doClick(level4);

	}

	// **************** wait Utils *************

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForPresenceOfElementsLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForVisiblityOfElementsLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
			return List.of();// return empty array list
		}
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresent(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element= wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	public WebElement waitForElementVisible(By locator, int timeout, int intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchElementException.class)
				.withMessage("========= Element not found =========");
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */

	public void clickWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	public String waitForTitleContains(String titleFraction, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("Title not found");

		}
		return driver.getTitle();
	}

	@Step("Waiting for the title and capturing it...")
	public String waitForTitleToBe(String titleValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("Title not found");

		}
		return driver.getTitle();
	}

	public String waitForURLContains(String urlFraction, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");

		}
		return driver.getCurrentUrl();
	}

	public String waitForURLToBe(String urlValue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL not found");

		}
		return driver.getCurrentUrl();
	}

	public Alert waitForJSAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public Alert waitForJSAlert(int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoAlertPresentException.class)
				.withMessage("======= No Alert found ========");

		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(int timeout) {
		Alert alert = waitForJSAlert(timeout);
		String text = alert.getText();
		alert.accept();
		return text;

	}

	public void acceptAlert(int timeout) {
		waitForJSAlert(timeout).accept();

	}

	public void dismissAlert(int timeout) {
		waitForJSAlert(timeout).dismiss();

	}

	public void alertSendKeys(int timeout, String value) {
		Alert alert = waitForJSAlert(timeout);
		alert.sendKeys(value);
		alert.accept();

	}

	// wait for iFrames/frame

	/**
	 * An expectation for checking whether the given frame is available to switch
	 * to. If the frame is available it switches the given driver to the specified
	 * frame.
	 * 
	 * @param frameLocator
	 * @param timeout
	 */
	public void waitForFrameByLocator(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	public void waitForFrameByLocator(By frameLocator, int timeOut, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(intervalTime)).ignoring(NoSuchFrameException.class)
				.withMessage("======= No Frame found ========");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));

	}

	public void waitForFrameByIndex(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));

	}

	public void waitForFrameByIndex(String frameIdOrName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));

	}

	public void waitForFrameByIndex(WebElement frameElement, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));

	}

	public boolean waitForWindowsToBe(int totalWindows, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindows));
	}

	public void isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String falg = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"))
				.toString();
		if (Boolean.parseBoolean(falg)) {
			System.out.println("Page is completely loaded");
		} else {
			throw new RuntimeException("Page is not loaded");
		}

	}

}
