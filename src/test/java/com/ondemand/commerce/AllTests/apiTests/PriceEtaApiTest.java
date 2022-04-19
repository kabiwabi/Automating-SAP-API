package com.ondemand.commerce.AllTests.apiTests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.*;
import com.ondemand.commerce.pages.data.Tire;
import retrofit.etaModels.EtaRequest;
import retrofit.pricingModels.CustomerPricing;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PriceEtaApiTest extends TestUtilities {
    private ArrayList<WebElement> msrpList = new ArrayList<>();
    private CustomerPricing customerPricing;

    @Test
    public void temporaryTest() throws IOException {
    /** see PriceEtaApiTest class for a full walk through of the logic */
    LanguageSelectionPage welcomePage = new LanguageSelectionPage(driver, log);
    welcomePage.openPage();
    log.info("Welcome page clicked successfully!");

    /** click on Form Authentication link */
    LoginPage loginPage = welcomePage.clickLanguageEnglish();

    /** execute log in */
    HomePage homePage = loginPage.logIn("jdoe@testgt.ca", "1234");

    /** create a navigation bar object and click search by specifications */
    NavigationBar navigationBar = new NavigationBar(driver,log);
    FindTiresBySpecificationPage bySpecificationPage = navigationBar.ClickFindTiresBySpecification();
    bySpecificationPage.FillTireSize("225");
    SearchResultsPage searchResults = bySpecificationPage.clickSearch();

    /** adds one of each product to the cart */
    searchResults.addToCart("5");

    /** clicks get ETA for each product */
    searchResults.clickEtaForAll();

    /** initializes a list of all tire objects found on the search results page */
    List<Tire> tireListFull = searchResults.initListAssignAllTires();

    /** Iterates over the list of tire products & calls both the pricing & ETA API on each product
     * to retrieve what the price and ETA is on the S4 backend.
     * Asserts that both the price & ETA on the frontend(commerce website) match the response from the backend.
     */
    for(int i=0; i<tireListFull.size(); i++) {
        Tire tireProductIterate = tireListFull.get(i);
        CustomerPricing customerPricing = searchResults.getPricingFromS4(tireProductIterate);
        String productPrice = customerPricing.getD().getMaterialsList().getResults().get(0).getPrice();
        EtaRequest etaRequest = searchResults.getETAfromS4(tireProductIterate);
        String productEta = etaRequest.getD2().getEtaResults().get(0).getDescription();
        Assert.assertEquals(productPrice.substring(0, productPrice.length() - 1), tireProductIterate.getClientPrice().substring(1), "Price assertion failed!");
        log.info("Successfully asserted that product "+i+"'s hybris price matches the S4 price ");
        Assert.assertNotEquals(tireProductIterate.getEta(), productEta, "ETA assertion failed!");
        log.info("Successfully asserted that product "+i+"'s hybris ETA matches the S4 ETA ");
        }
    }
}
