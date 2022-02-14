package com.ondemand.commerce.temporarytests;

import com.ondemand.commerce.base.TestUtilities;
import com.ondemand.commerce.pages.*;
import com.ondemand.commerce.pages.data.Tire;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TemporaryTests extends TestUtilities {
    private ArrayList<WebElement> msrpList = new ArrayList<>();

    @Test
    public void temporaryTest() {
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
        searchResults.clickPlusOneForAll();
        searchResults.clickEtaForAll();
//        searchResults.clickAddToCartForAll();

        List<Tire> tireList = new ArrayList<>();
        searchResults.initializeTireList(searchResults.getSkuList(), tireList);
        searchResults.assignAllTire(searchResults.getSkuList(),searchResults.getTireNameList(),searchResults.getMSRPList(),searchResults.getSuggPriceList(),
                                    searchResults.getLocalList(),searchResults.getWarehouseList(),searchResults.getEtaList(), tireList);
        searchResults.printAllTires(tireList);
//        ShoppingCartPage shoppingCartPage = navigationBar.ClickShoppingCart();
//        shoppingCartPage.RemoveAllItems();
        sleep(10000);
    }
}
