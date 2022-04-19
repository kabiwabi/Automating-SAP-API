package com.ondemand.commerce.pages;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

	protected WebDriver driver;
	protected Logger log;
	public String productSku;

	public BasePageObject(WebDriver driver, Logger log) {
		this.driver = driver;
		this.log = log;
	}

	/** Open page with given URL */
	protected void openUrl(String url) {
		driver.get(url);
	}

	/** Find element using given locator */
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}

	/** Find all elements using given locator */
	protected List<WebElement> findAll(By locator) {
		return driver.findElements(locator);
	}

	/** Click on element with given locator when its visible */
	protected void click(By locator) {
		waitForVisibilityOf(locator, 30);
		find(locator).click();
	}

	/** Click on element when its visible */
	protected void elementClick(WebElement element) {
		waitForVisibilityOfElement(element, 30);
		element.click();
	}

	/** Click on element when visible
	 *  check that the confirmation box appears and waits for it to disappear
	 */
	protected void clickAndWait(WebElement element, By popupLocator) {
		sleepNow(500);
		waitForVisibilityOfElement(element, 30);
		element.click();
		waitForVisibilityOf(popupLocator, 30);
		waitForInvisibilityOf(popupLocator, 30);
	}

	/** Click on element when visible
	 *  wait for pop-up to be visible, then click the pop-up
	 */
	protected void clickAndClose(WebElement element, By popUplocator) {
		waitForVisibilityOfElement(element, 30);
		element.click();
		waitForVisibilityOf(popUplocator, 30);
		click(popUplocator);
	}

	/** Type given text into element with given locator */
	protected void type(String text, By locator) {
		waitForVisibilityOf(locator, 30);
		find(locator).sendKeys(text);
	}

	/** Get URL of current page from browser */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/** Get title of current page */
	public String getCurrentPageTitle() {
		return driver.getTitle();
	}

	/** Get source of current page */
	public String getCurrentPageSource() {
		return driver.getPageSource();
	}

	/**
	 * Wait for specific ExpectedCondition for the given amount of time in seconds
	 */
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(condition);
	}

	/**
	 * Wait for specific ExpectedCondition for the given amount of time in seconds (boolean argument)
	 */
	private void waitForBoolean(ExpectedCondition<Boolean> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(condition);
	}

	/**
	 * Wait for given number of seconds for element with given locator to be visible
	 * on the page
	 */
	protected void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
		int attempts = 0;
		while (attempts < 10) {
			try {
				waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
						(timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	/** accepts a locator & integer,
	 * waits for "integer" seconds for the web element identified by the locator to become invisible
	 */
	protected void waitForInvisibilityOf(By locator, Integer... timeOutInSeconds) {
		int attempts = 0;
		while (attempts < 10) {
			try {
				waitForBoolean(ExpectedConditions.invisibilityOfElementLocated(locator),
						(timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	/** accepts a web element object & integer,
	* waits for "integer" seconds for the web element identified by the locator to become invisible
	 * */
	protected void waitForVisibilityOfElement(WebElement element, Integer... timeOutInSeconds) {
		int attempts = 0;
		while (attempts < 10) {
			try {
				waitFor(ExpectedConditions.visibilityOf(element),
						(timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
	}

	/** Wait for alert present and then switch to it */
	protected Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.alertIsPresent());
		return driver.switchTo().alert();
	}

	/** Switch to iFrame using it's locator */
	protected void switchToFrame(By frameLocator) {
		driver.switchTo().frame(find(frameLocator));
	}

	/** Press Key on locator */
	protected void pressKey(By locator, Keys key) {
		find(locator).sendKeys(key);
	}

	/** Press Key using Actions class */
	public void pressKeyWithActions(Keys key) {
		log.info("Pressing " + key.name() + " using Actions class");
		Actions action = new Actions(driver);
		action.sendKeys(key).build().perform();
	}

	/** Perform scroll to the bottom */
	public void scrollToBottom() {
		log.info("Scrolling to the bottom of the page");
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/** Perform scroll to the top */
	public void scrollToTop() {
		WebElement element = driver.findElement(By.tagName("header"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	/** Add cookie */
	public void setCookie(Cookie ck) {
		log.info("Adding cookie " + ck.getName());
		driver.manage().addCookie(ck);
		log.info("Cookie added");
	}

	/** Get cookie value using cookie name */
	public String getCookie(String name) {
		log.info("Getting value of cookie " + name);
		return driver.manage().getCookieNamed(name).getValue();
	}

	/** sleep for milis seconds */
	protected static void sleepNow(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
