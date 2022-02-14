package com.ondemand.commerce.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ShoppingCartPage extends NavigationBar{

    private By removeButtonLocator = By.xpath("//button[@class='btn js-cartItemRemoveBtn']");
    private By closePopupButtonLocator = By.xpath("//button[@class='close closeAccAlert close-btn']");
    private By checkoutButtonLocator = By.xpath("//button[@class='btn btn-lg btn-primary btn--continue-checkout js-checkout-button']");

    public ShoppingCartPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    public void RemoveAllItems() {
        List<WebElement> listRemoveButtons = findAll(removeButtonLocator);
        Integer listSize = listRemoveButtons.size();
        log.info("There are: " + listSize + " items in the cart to be removed");
        for (int i=0; i<listSize; i++) {
            clickAndClose(find(removeButtonLocator), closePopupButtonLocator);
        }
    }

    public CheckoutPage ClickCheckout() {
        log.info("Clicking the checkout button");
        click(checkoutButtonLocator);
        return new CheckoutPage(driver,log);
    }

}
