package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LanguageSelectionPage extends BasePageObject {

	private String pageUrl = "https://accstorefront.cb46rr50dx-groupetou1-d1-nat.model-t.cc.commerce.ondemand.com/touchettestorefront/touchette/en/";
	private By englishLanguageButtonLocator = By.xpath("//button[@data-lang='en']");
	private By frenchLanguageButton = By.xpath("//button[@data-lang='fr']");
	
	public LanguageSelectionPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	/** Open WelcomePage with it's url */
	public void openPage() {
		log.info("Opening page: " + pageUrl);
		openUrl(pageUrl);
		log.info("Page opened!");
	}

	/** Select language option as english */
	public LoginPage clickLanguageEnglish() {
		log.info("Successfully selected english language");
		click(englishLanguageButtonLocator);
		return new LoginPage(driver, log);
	}

}
