package com.ondemand.commerce.temporarytests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.*;
import retrofit.etaModels.EtaRequest;
import retrofit.model.pricingModels.CustomerPricing;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

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
    // create a navigation bar instance and click search by specifications
    NavigationBar navigationBar = new NavigationBar(driver,log);
    FindTiresBySpecificationPage bySpecificationPage = navigationBar.ClickFindTiresBySpecification();
    bySpecificationPage.FillTireSize("225");
    SearchResultsPage searchResults = bySpecificationPage.clickSearch();
//    // adds one of each product to the cart
//    searchResults.addToCart("5");
//    // clicks get ETA for each product
//    searchResults.clickEtaForAll();
//    // initialize a list to store attributes of each product
//    List<Tire> tireList = new ArrayList<>();
//    searchResults.initializeTireList(searchResults.getSkuList(), tireList);
//    // store characteristics of the product as an attribute of the object
//    searchResults.assignAllTire(searchResults.getCompleteSkuList(),searchResults.getTireNameList(),searchResults.getMSRPList(),searchResults.getSuggPriceList(),
//                                searchResults.getLocalList(),searchResults.getWarehouseList(),searchResults.getEtaList(), tireList);
//
//    searchResults.printAllTires(tireList);
    CustomerPricing customerPricing = searchResults.getPricingFromS4();
    String materialsList = customerPricing.getD().getMaterialsList().getResults().get(0).getPrice();
    EtaRequest etaRequest = searchResults.getETAfromS4();
    String etaList = etaRequest.getD2().getEtaResults().get(0).getDescription();
    System.out.println("The price retrieved from S4 is :" + materialsList);
    System.out.println("The expected delivery date retrieved from S4 is :" + etaList);

//    ShoppingCartPage shoppingCartPage = navigationBar.ClickShoppingCart();
//    shoppingCartPage.RemoveAllItems();
    }
}
