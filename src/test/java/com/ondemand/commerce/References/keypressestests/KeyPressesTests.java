package com.ondemand.commerce.References.keypressestests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.Reference.KeyPressesPage;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class KeyPressesTests extends TestUtilities {

	@Test
	public void pressKeyTest() {
		log.info("Starting pressKeyTest");

		// open KeyPressesPage
		KeyPressesPage keyPressesPage = new KeyPressesPage(driver, log);
		keyPressesPage.openPage();

		// Push keyboard key
		keyPressesPage.pressKey(Keys.ENTER);

		// Get Results text
		String result = keyPressesPage.getResultText();

		// Verify Result text is expected
		Assert.assertTrue(result.equals("You entered: ENTER"),
				"result is not expected. \nShould be 'You entered: ENTER', but it is '" + result + "'");
	}

	@Test
	public void pressKeyWithActionsTest() {
		log.info("Starting pressKeyWithActionsTest");

		// open KeyPressesPage
		KeyPressesPage keyPressesPage = new KeyPressesPage(driver, log);
		keyPressesPage.openPage();

		// Push keyboard key
		keyPressesPage.pressKeyWithActions(Keys.SPACE);

		// Get Results text
		String result = keyPressesPage.getResultText();

		// Verify Result text is expected
		Assert.assertTrue(result.equals("You entered: SPACE"),
				"result is not expected. \nShould be 'You entered: ENTER', but it is '" + result + "'");
	}
}
