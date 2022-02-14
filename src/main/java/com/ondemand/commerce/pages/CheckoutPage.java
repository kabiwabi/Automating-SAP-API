package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends ShoppingCartPage {
    public CheckoutPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    private By placeOrderButtonlocator = By.xpath("//button[@id='singleCheckoutPlaceOrder']");
}
