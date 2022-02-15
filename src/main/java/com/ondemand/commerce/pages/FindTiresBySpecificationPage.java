package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FindTiresBySpecificationPage extends NavigationBar {

    //locator for search button
    private By searchButtonLocator = By.xpath("//button[@id='tireSizeSearch-js-submit-search']");

    //locators for input fields
    private By tireSizeInputFieldLocator = By.xpath("//input[@id='js-tire-size']");
    private By itemSkuInputFieldLocator = By.xpath("//input[@id='js-tire-sku']");
    private By brandInputFieldLocator = By.xpath("//input[@id='js-tire-manufacturer']");
    private By modelInputFieldLocator = By.xpath("//input[@id='js-tire-model']");
    private By descriptionInputFieldLocator = By.xpath("//input[@id='js-tire-description']");

    //locators for checkboxes
    private By addRearTireSizeCheckboxLocator = By.xpath("//label[@for='show-tire-rear-size-tireSizeSearch']");
    private By summerApprovedCheckboxLocator = By.xpath("//label[@for='tireSizeSearch-js-vehicle-summerAllSeason']");
    private By winterApprovedCheckboxLocator = By.xpath("//label[@for='tireSizeSearch-js-vehicle-winter']");
    private By allWeatherCheckboxLocator = By.xpath("//label[@for='tireSizeSearch-js-vehicle-AllWeather']");
    private By allCheckboxLocator = By.xpath("//label[@for='tireSizeSearch-js-vehicle-AllSeason']");
    private By onSaleCheckboxLocator = By.xpath("//label[@for='on-sale-only-tireSizeSearch']");

    //clicks the search button, returns a "SearchResultsPage" object
    public SearchResultsPage clickSearch() {
        log.info("Clicking the search button");
        click(searchButtonLocator);
        return new SearchResultsPage(driver, log);
    }

    //accepts a string, fills the "tire size" field with a tire size
    public void FillTireSize(String tireSize) {
        log.info("Filling the tire size field with: "+ tireSize);
        type(tireSize, tireSizeInputFieldLocator);
    }

    //constructor for the "FindTiresBySpecificationPage" object
    public FindTiresBySpecificationPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    public void SelectAddRearTireSize() {
        click(addRearTireSizeCheckboxLocator);
    }

    public void SelectSummerApproved() {
        click(summerApprovedCheckboxLocator);
    }

    public void SelectWinterApproved() {
        click(winterApprovedCheckboxLocator);
    }

    public void SelectAllWeather() {
        click(allWeatherCheckboxLocator);
    }

    public void SelectAll() {
        click(allCheckboxLocator);
    }
}

