package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends NavigationBar {

	/** URL for the hybris storefront */
	private String pageUrl = "https://accstorefront.cb46rr50dx-groupetou1-d1-nat.model-t.cc.commerce.ondemand.com/touchettestorefront/touchette/en/login";

	/** locators for logout button */
	private By logOutButton = By.xpath("//a[normalize-space()='Sign out']");

	/** locator for the confirmation message that the user has logged in */
	private By message = By.xpath("//li[@id='logged_in-user']");

	/** constructor for "HomePage" object */
	public HomePage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	/** Get URL variable from PageObject */
	public String getPageUrl() {
		return pageUrl;
	}

	/** Verification if logOutButton is visible on the page */
	public boolean isLogOutButtonVisible() {
		return find(logOutButton).isDisplayed();
	}

	/** Return text from success message */
	public String getSuccessMessageText() {
		return find(message).getText();
	}

}
