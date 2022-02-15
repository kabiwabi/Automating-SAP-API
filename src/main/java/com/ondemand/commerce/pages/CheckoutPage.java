package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends ShoppingCartPage {
    //locator for the place order button
    private By placeOrderButtonLocator = By.xpath("//button[@id='singleCheckoutPlaceOrder']");

    //constructor for checkout page
    public CheckoutPage(WebDriver driver, Logger log) {
        super(driver, log);
    }
}
