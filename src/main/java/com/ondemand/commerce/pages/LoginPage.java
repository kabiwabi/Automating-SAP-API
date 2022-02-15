package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject {

	private By usernameLocator = By.xpath("//input[@id='j_username']");
	private By passwordLocator = By.xpath("//input[@id='j_password']");
	private By logInButtonLocator = By.xpath("//button[@type='submit']");
	private By errorMessageLocator = By.xpath("//p[normalize-space()='Your username or password was incorrect.']");

	public LoginPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	//method accepts two strings(user/PW) and logs in with them
	public HomePage logIn(String username, String password) {
		log.info("Executing LogIn with username [" + username + "] and password [" + password + "]");
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(logInButtonLocator);
		return new HomePage(driver, log);
	}

	public void negativeLogIn(String username, String password) {
		log.info("Executing Negative LogIn with username [" + username + "] and password [" + password + "]");
		type(username, usernameLocator);
		type(password, passwordLocator);
		click(logInButtonLocator);
	}

	/** Wait for error message to be visible on the page */
	public void waitForErrorMessage() {
		waitForVisibilityOf(errorMessageLocator, 5);
	}

	public String getErrorMessageText() {
		return find(errorMessageLocator).getText();
	}

}
