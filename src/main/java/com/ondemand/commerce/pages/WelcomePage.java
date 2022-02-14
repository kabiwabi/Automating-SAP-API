package com.ondemand.commerce.pages;

import com.ondemand.commerce.pages.Reference.*;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WelcomePage extends BasePageObject {

	private String pageUrl = "https://accstorefront.cb46rr50dx-groupetou1-s1-nat.model-t.cc.commerce.ondemand.com/touchettestorefront/touchette/en/";

	private By formAuthenticationLinkLocator = By.linkText("Form Authentication");
	private By englishLanguageButton = By.xpath("//button[@data-lang='en']");
	private By checkboxesLinkLocator = By.linkText("Checkboxes");
	private By dropdownLinkLocator = By.linkText("Dropdown");
	private By javaScriptAlertsLinkLocator = By.linkText("JavaScript Alerts");
	private By multipleWindowsLinkLocator = By.linkText("Multiple Windows");
	private By editorLinkLocator = By.linkText("WYSIWYG Editor");
	
	public WelcomePage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	/** Open WelcomePage with it's url */
	public void openPage() {
		log.info("Opening page: " + pageUrl);
		openUrl(pageUrl);
		log.info("Page opened!");
	}

	public LoginPage clickLanguageEnglish() {
		log.info("Confirming login page");
		click(englishLanguageButton);
		return new LoginPage(driver, log);
	}

	/** Open CheckboxesPage by clicking on Checkboxes Link */
	public CheckboxesPage clickCheckboxesLink() {
		log.info("Clicking Checkboxes link on Welcome Page");
		click(checkboxesLinkLocator);
		return new CheckboxesPage(driver, log);
	}

	public DropdownPage clickDropdownLink() {
		log.info("Clicking Dropdown link on Welcome Page");
		click(dropdownLinkLocator);
		return new DropdownPage(driver, log);
	}

	public JavaScriptAlertsPage clickJavaScriptAlertsLink() {
		log.info("Clicking JavaScript Alerts link on Welcome Page");
		click(javaScriptAlertsLinkLocator);
		return new JavaScriptAlertsPage(driver, log);
	}

	public WindowsPage clickMultipleWindowsLink() {
		log.info("Clicking Multiple Windows link on Welcome Page");
		click(multipleWindowsLinkLocator);
		return new WindowsPage(driver, log);
	}

	public EditorPage clickWYSIWYGEditorLink() {
		log.info("Clicking WYSIWYG Editor link on Welcome Page");
		click(editorLinkLocator);
		return new EditorPage(driver, log);
	}

}
