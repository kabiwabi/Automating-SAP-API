package com.ondemand.commerce.pages;

import com.ondemand.commerce.pages.data.Tire;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

public class SearchResultsPage extends NavigationBar{

    private By addToCartLocator = By.xpath("//button[@class='quantity-input__btn quantity-input__btn--up'][normalize-space()='+']");
    private By getEtaLocator = By.xpath("//button[@class='btn btn-primary btn-small viewETA viewETA--search']");
    private By addToOrderLocator = By.xpath("//button[@class='addToCartButton btn btn-primary btn-lg']");
    private By notificationMessageLocator = By.xpath("//p[@class='c-cart-notification__message']");

    private By msrpLocator = By.xpath("//*[@class='search-results__price-item search-results__price-item--msrp']");
    private By suggestedPriceLocator = By.xpath("//*[@class='search-results__price-item search-results__price-item-suggested']");
    private By skuLocator = By.xpath("//div[@class='sku']");
    private By tireNameLocator = By.xpath("//h2[@class='search-results__description']");
    private By localAvailLocator = By.xpath("//div[@class='search-results__on-hand-item search-results__on-hand-item--local_count']//p");
    private By warehouseAvailLocator = By.xpath("//div[@class='search-results__on-hand-item search-results__on-hand-item--warehouse_count']//p");
    private By etaLocator = By.xpath("//table[@class='item__eta_table table-striped not-visible']//td[2]");

    public SearchResultsPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    //clicks the "+1" button for each item displayed on the search results page
    public void clickPlusOneForAll() {
        log.info("Adding one of each item to count");
        scrollToTop();
        List<WebElement> listAddToCart = findAll(addToCartLocator);
        listAddToCart.stream().forEach(this::elementClick);
    }

    //clicks "Get ETA" for each item displayed on the search results page
    public void clickEtaForAll() {
        log.info("Clicking \"GET ETA\" for each item");
        scrollToTop();
        List<WebElement> listGetEta = findAll(getEtaLocator);
        listGetEta.stream().forEach(this::elementClick);
    }

    //clicks "add to cart" for each item displayed on the search results page
    public void clickAddToCartForAll() {
        log.info("Adding one of each item to cart");
        scrollToTop();
        List<WebElement> listAddToCart = findAll(addToOrderLocator);
        listAddToCart.stream().forEach(element -> clickAndWait(element,notificationMessageLocator));
    }

    //retrieves a list of web elements identified by a locator, returns the list in string form
    public List getLocatorList(By locator) {
        List<WebElement> webElementList = findAll(locator);
        List<String> locatorStringList;
        locatorStringList = webElementList.stream().map(element -> element.getAttribute("innerText")).collect(Collectors.toList());
        return locatorStringList;
    }

    //calls the getLocatorList method for a specific locator (sku, tire name, price etc)
    public List getSkuList() {
        List<String> tempList = getLocatorList(skuLocator);
        return tempList;
    }

    public List getTireNameList() {
        List<String> tempList = getLocatorList(tireNameLocator);
        return tempList;
    }


    public List getMSRPList() {
        List<String> tempList = getLocatorList(msrpLocator);
        return tempList;
    }

    public List getSuggPriceList() {
        List<String> tempList = getLocatorList(suggestedPriceLocator);
        return tempList;
    }

    public List getLocalList() {
        List<String> tempList = getLocatorList(localAvailLocator);
        return tempList;
    }

    public List getWarehouseList() {
        List<String> tempList = getLocatorList(warehouseAvailLocator);
        return tempList;
    }

    public List getEtaList() {
        List<String> tempList = getLocatorList(etaLocator);
        return tempList;
    }

    //accepts a list of attributes (sku, tire name etc) & empty list of tire objects, counts the number of items
    //populates the tire list with "count" number of empty tire objects
    public void initializeTireList(List<String> attributeStringList, List<Tire> tireList) {
        int listSize = attributeStringList.size();
        for (int i=0; i<listSize; i++) {
            tireList.add(new Tire());
        }
    }

    //assigns all tire objects all attributes based search results
    public void assignAllTire (List<String> skuList, List<String> tireNameList, List<String> msrpList,
                           List<String> suggPriceList, List<String> localAvailList,
                           List<String> warehouseAvailList, List<String> etaList, List<Tire> tireList) {

        int counter = 0;
        for (Tire temp : tireList) {
            temp.setSku(skuList.get(counter));
            temp.setTireName(tireNameList.get(counter));
            temp.setMsrp(msrpList.get(counter));
            temp.setSuggestedPrice(suggPriceList.get(counter));
            temp.setLocalAvailability(localAvailList.get(counter));
            temp.setWarehouseAvailability(warehouseAvailList.get(counter));
            temp.setEta(etaList.get(counter));
            counter++;
        }
    }

    public void printAllTires(List<Tire> tireList) {
        tireList.stream().forEach(System.out::println);
    }

    public void printFromList(List<String> myList) {
        myList.stream().forEach(System.out::println);
    }

}
