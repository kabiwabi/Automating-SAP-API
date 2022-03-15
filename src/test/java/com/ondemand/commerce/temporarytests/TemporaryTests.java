package com.ondemand.commerce.temporarytests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.*;
import com.ondemand.commerce.pages.data.Tire;
import retrofit.etaModels.EtaRequest;
import retrofit.pricingModels.CustomerPricing;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrofit.service.ApiMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemporaryTests extends TestUtilities {
    private ArrayList<WebElement> msrpList = new ArrayList<>();
    private CustomerPricing customerPricing;

    @Test
    public void temporaryTest() throws IOException {
    // open main page
    WelcomePage welcomePage = new WelcomePage(driver, log);
    welcomePage.openPage();
    takeScreenshot("WelcomePage opened");
    // Click on Form Authentication link
    LoginPage loginPage = welcomePage.clickLanguageEnglish();
    takeScreenshot("LoginPage opened");
    // execute log in
    HomePage homePage = loginPage.logIn("jdoe@testgt.ca", "1234");
    takeScreenshot("HomePage opened");
    // create a navigation bar instance and click search by specifications
    NavigationBar navigationBar = new NavigationBar(driver,log);
    FindTiresBySpecificationPage bySpecificationPage = navigationBar.ClickFindTiresBySpecification();
    bySpecificationPage.FillTireSize("225");

    SearchResultsPage searchResults = bySpecificationPage.clickSearch();
    // adds one of each product to the cart
    searchResults.addToCart("5");
    // clicks get ETA for each product
    searchResults.clickEtaForAll();
    List<Tire> tireListInit = searchResults.initListAssignAllTires();
    System.out.println(tireListInit.get(0).getSku());
    CustomerPricing customerPricing = searchResults.getPricingFromS4(tireListInit);
    String productPrice = customerPricing.getD().getMaterialsList().getResults().get(0).getPrice();
    EtaRequest etaRequest = searchResults.getETAfromS4(tireListInit);
    String etaList = etaRequest.getD2().getEtaResults().get(0).getDescription();
    System.out.println("The product price retrieved from S4 is :" + productPrice);
    System.out.println("The expected delivery date retrieved from S4 is :" + etaList);

    Assert.assertEquals(productPrice.substring(0, productPrice.length() - 1), tireListInit.get(0).getClientPrice().substring(1), "It failed!");

//    ShoppingCartPage shoppingCartPage = navigationBar.ClickShoppingCart();
//    shoppingCartPage.RemoveAllItems();
    }
}
