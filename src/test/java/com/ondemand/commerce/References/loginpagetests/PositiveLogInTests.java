package com.ondemand.commerce.References.loginpagetests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.HomePage;
import com.ondemand.commerce.pages.LoginPage;
import com.ondemand.commerce.pages.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveLogInTests extends TestUtilities {

	@Test
	public void logInTest() {
		// open main page
		WelcomePage welcomePage = new WelcomePage(driver, log);
		welcomePage.openPage();
		takeScreenshot("WelcomePage opened");

		// Click on Form Authentication link
		LoginPage loginPage = welcomePage.clickLanguageEnglish();
		takeScreenshot("LoginPage opened");
		
		// execute log in
		HomePage homePage = loginPage.logIn("saptest", "12341234");
		takeScreenshot("HomePage opened");

		// Verifications
		// New page url is expected
		Assert.assertEquals(homePage.getCurrentUrl(), homePage.getPageUrl());

		// log out button is visible
		Assert.assertTrue(homePage.isLogOutButtonVisible(), "LogOut Button is not visible.");

		// Successful log in message
		String expectedSuccessMessage = "Welcome, SAP";
		String actualSuccessMessage = homePage.getSuccessMessageText();
		Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
				"actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
						+ expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);
	}
}
