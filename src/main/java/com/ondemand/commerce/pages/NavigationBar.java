package com.ondemand.commerce.pages;

import com.ondemand.commerce.base.TestUtilities;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationBar extends BasePageObject{

    /** locators for all the buttons for the navigation bar on top of the page */
    private By homeButtonLocator = By.xpath("//a[@title='Home']");
    private By productSearchButtonLocator = By.xpath("//a[@title='Product search']");
    private By findTiresBySpecificationsLocator = By.xpath("//a[@title='Find tires by specifications']");
    private By bookingsButtonLocator = By.xpath("//a[@title='Bookings']");
    private By promotionsButtonLocator = By.xpath("//a[@title='Promotions']");
    private By toolsButtonLocator = By.xpath("//a[@title='Tools']");
    private By communicationsButtonLocator = By.xpath("//a[@title='Communications']");
    private By orderHistoryButtonLocator = By.xpath("//a[@href='#'][normalize-space()='Order history']");
    private By helpButtonLocator = By.xpath("//a[@href='#'][normalize-space()='Order history']");
    private By contactUsButtonLocator = By.xpath("//a[@title='Contact us']");
    private By shoppingCartButtonLocator = By.xpath("//a[@class='mini-cart-link js-mini-cart-link']");

    /** constructor for "NavigationBar" object */
    public NavigationBar(WebDriver driver, Logger log) {
        super(driver, log);
    }

    /** clicks the home page button and returns a "HomePage" object */
    public HomePage ClickHome() {
        log.info("Clicking the home button");
        click(homeButtonLocator);
        return new HomePage(driver, log);
    }

    /** clicks the shopping cart button and returns a "Shopping Cart" object */
    public ShoppingCartPage ClickShoppingCart() {
        log.info("Clicking the shopping cart button");
        click(shoppingCartButtonLocator);
        return new ShoppingCartPage(driver, log);
    }

    /** clicks the "search button" and clicks the "find tires by specification" button */
    public FindTiresBySpecificationPage ClickFindTiresBySpecification() {
        log.info("Clicking the product search button");
        click(productSearchButtonLocator);
        log.info("Clicking the find tires by specification button");
        click(findTiresBySpecificationsLocator);
        return new FindTiresBySpecificationPage(driver, log);
    }
}
