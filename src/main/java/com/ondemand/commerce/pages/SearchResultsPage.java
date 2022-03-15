package com.ondemand.commerce.pages;

import com.ondemand.commerce.pages.data.Tire;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import retrofit.etaModels.*;
import retrofit.pricingModels.*;
import retrofit.pricingModels.*;
import retrofit.service.Client;
import retrofit.service.EtaService;
import retrofit.service.PricingService;
import retrofit2.Call;
import retrofit2.Response;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SearchResultsPage extends NavigationBar{

    //locators for buttons
    private By addToCartLocator = By.xpath("//button[@class='quantity-input__btn quantity-input__btn--up'][normalize-space()='+']");
    private By getEtaLocator = By.xpath("//button[@class='btn btn-primary btn-small viewETA viewETA--search']");
    private By addToOrderLocator = By.xpath("//button[@class='addToCartButton btn btn-primary btn-lg']");
    private By quantityLocator = By.xpath("//div[@class='quantity-input']//input");

    //locator for the popup that occurs when you press "GET ETA"
    private By notificationMessageLocator = By.xpath("//p[@class='c-cart-notification__message']");

    //locators for all fields we store about the tire products
    private By msrpLocator = By.xpath("//*[@class='search-results__price-item search-results__price-item--msrp']");
    private By suggestedPriceLocator = By.xpath("//*[@class='search-results__price-item search-results__price-item-suggested']");
    private By skuLocator = By.xpath("//div[@class='sku']");
    private By tireNameLocator = By.xpath("//h2[@class='search-results__description']");
    private By localAvailLocator = By.xpath("//div[@class='search-results__on-hand-item search-results__on-hand-item--local_count']//p");
    private By warehouseAvailLocator = By.xpath("//div[@class='search-results__on-hand-item search-results__on-hand-item--warehouse_count']//p");
    private By etaLocator = By.xpath("//table[@class='item__eta_table table-striped not-visible']//td[2]");
    private By pleaseWaitLocator = By.xpath("//div[@class='loading-overlay__inner']");
    private By completeSkuLocator = By.xpath("//input[@type='hidden'][@class='productCodePost']");
    private static By clientPriceLocator = By.xpath("//dl[@class='search-results__pdm-content search-results__pdm-prices']//dt[contains(text(), 'P')]/following-sibling::dt[contains(text(), 'D')]/preceding-sibling::dd");

    private String baseURL;
    private String driverPath;
    private WebDriver driver;
    private String asmUserName;
    private String password;
    private String customerNumber;
    private String customerName;
    private String webdriverHeadless;
    private String s4BaseURL;
    private String s4AuthorizationName;
    private String s4AuthorizationPassword;
    private List<String> xmlPriceListTypes = new ArrayList<String>();
    private List<String> xmlMaterialsList = new ArrayList<String>();
    public static String FILENAME = "";

    public SearchResultsPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    public List getCompleteSkuList() {
        List<WebElement> listCompleteSku = getLocatorSkuList(completeSkuLocator);
        return listCompleteSku;
    }

    //clicks the "+1" button for each item displayed on the search results page
    public void addToCart(String quantityToAdd) {
        log.info("Adding one of each item to count");
        scrollToTop();
        List<WebElement> listAddToCart = findAll(quantityLocator);
        listAddToCart.stream().forEach( element -> element.sendKeys(quantityToAdd));
    }

    //clicks "Get ETA" for each item displayed on the search results page
    public void clickEtaForAll() {
        log.info("Clicking \"GET ETA\" for each item");
        scrollToTop();
        List<WebElement> listGetEta = findAll(getEtaLocator);
        listGetEta.stream().forEach(element -> clickAndWait(element, pleaseWaitLocator));
    }

    //clicks "add to cart" for each item displayed on the search results page
    public void clickAddToCartForAll() {
        log.info("Adding one of each item to cart");
        scrollToTop();
        List<WebElement> listAddToCart = findAll(addToOrderLocator);
        listAddToCart.stream().forEach(element -> clickAndWait(element,notificationMessageLocator));
    }

    //retrieves a list of web elements identified by a locator, returns the list in string form
    public  List getLocatorList(By locator) {
        List<WebElement> webElementList = findAll(locator);
        List<String> locatorStringList;
        locatorStringList = webElementList.stream().map(element -> element.getAttribute("innerText")).collect(Collectors.toList());
        return locatorStringList;
    }

    public List getLocatorSkuList(By locator) {
        List<WebElement> webElementList = findAll(locator);
        List<String> locatorStringList;
        locatorStringList = webElementList.stream().map(element -> element.getAttribute("value")).collect(Collectors.toList());
        return locatorStringList;
    }

    //getters for all the fields we store about each tire product
    public  List getSkuList() {
        List<String> tempList = getLocatorList(skuLocator);
        return tempList;
    }

    public  List getTireNameList() {
        List<String> tempList = getLocatorList(tireNameLocator);
        return tempList;
    }


    public  List getMSRPList() {
        List<String> tempList = getLocatorList(msrpLocator);
        return tempList;
    }

    public  List getSuggPriceList() {
        List<String> tempList = getLocatorList(suggestedPriceLocator);
        return tempList;
    }

    public  List getLocalList() {
        List<String> tempList = getLocatorList(localAvailLocator);
        return tempList;
    }

    public  List getWarehouseList() {
        List<String> tempList = getLocatorList(warehouseAvailLocator);
        return tempList;
    }

    public  List getEtaList() {
        List<String> tempList = getLocatorList(etaLocator);
        return tempList;
    }

    public  List getClientPriceList() {
        List<String> tempList = getLocatorList(clientPriceLocator);
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
    public void assignAllTire(List<String> skuList, List<String> tireNameList, List<String> msrpList,
                                     List<String> suggPriceList, List<String> localAvailList,
                                     List<String> warehouseAvailList, List<String> etaList, List<String> clientPriceList, List<Tire> tireList) {

        int counter = 0;
        for (Tire temp : tireList) {
            temp.setSku(skuList.get(counter));
            temp.setTireName(tireNameList.get(counter));
            temp.setMsrp(msrpList.get(counter));
            temp.setSuggestedPrice(suggPriceList.get(counter));
            temp.setLocalAvailability(localAvailList.get(counter));
            temp.setWarehouseAvailability(warehouseAvailList.get(counter));
            temp.setEta(etaList.get(counter));
            temp.setClientPrice(clientPriceList.get(counter));
            counter++;
        }
    }

    public List<Tire> initListAssignAllTires() {
        List<Tire> tireList = new ArrayList<>();
        initializeTireList(getSkuList(), tireList);
        assignAllTire(getSkuList(), getTireNameList(), getMSRPList(), getSuggPriceList(), getLocalList(), getWarehouseList(), getEtaList(), getClientPriceList(), tireList);
        return tireList;
    }

    public static void printAllTires(List<Tire> tireList) {
        tireList.stream().forEach(System.out::println);
    }

    public static void printFromList(List<String> myList) {
        myList.stream().forEach(System.out::println);
    }

    public RequestPricingBody initialRequestBodyJson(List<Tire> myList) {

        List<RequestPriceListTypesResult> requestPriceListTypesResults = new ArrayList<RequestPriceListTypesResult>();
        requestPriceListTypesResults.add(new RequestPriceListTypesResult("ZD"));
        RequestPriceListTypes requestPriceListTypes = new RequestPriceListTypes();
        requestPriceListTypes.setResults(requestPriceListTypesResults);

        List<RequestMaterialsListResult> requestMaterialsListResults = new ArrayList<RequestMaterialsListResult>();
        requestMaterialsListResults.add(new RequestMaterialsListResult(myList.get(0).getSku()));
        RequestMaterialsList requestMaterialsList = new RequestMaterialsList();
        requestMaterialsList.setResults(requestMaterialsListResults);

        RequestPricingBody requestPricingBody = new RequestPricingBody();
        requestPricingBody.setCustomerNumber("0001006563");
        requestPricingBody.setMaterialsList(requestMaterialsList);
        requestPricingBody.setPriceListTypes(requestPriceListTypes);
        requestPricingBody.setNoEmptyLiquidationPrice(false);
        requestPricingBody.setSortByQty(true);
        return requestPricingBody;
    }

    public CustomerPricing getPricingFromS4(List<Tire> myList) throws IOException {
        String csrfToken = "";
        PricingService service = Client.getRetrofitInstance("http://tch-s4hds1.grtouchette.com:8000/", "kwang", "Kabin321!!!")
                .create(PricingService.class);
        Call<ResponseBody> call = service.getPricingCSRFToken();

        Response<ResponseBody> response = call.execute();
        //get x-csrf-token header
        Headers headers = response.headers();
        for (String name : headers.toMultimap().keySet()) {
            if (name != null && "x-csrf-token".equalsIgnoreCase(name)) {
                csrfToken = headers.get(name);
                break;
            }
        }
        //check x-csrf-token is empty or not
        if (csrfToken.isEmpty()) {
            System.out.println("Didn't receive a x-csrf-token! Exit abnormal!");
            System.exit(1);
        }
        //get response' cookie set
        List<String> cookieList = response.headers().values("set-cookie");

        RequestPricingBody requestPricingBody = initialRequestBodyJson(myList);
        Call<CustomerPricing> callJson = service.postPricingSearchGson(requestPricingBody, csrfToken, cookieList);
        Response<CustomerPricing> customerPricingResponse = callJson.execute();
        if (customerPricingResponse.isSuccessful()) {
            System.out.println("Retrieve Pricing from S4 API is successful");
        } else {
            System.out.println("Retrieve Pricing from S4 API failed! Exit abnormal!");
            System.exit(1);
        }
        //Got Response body in JSON and converted to POJO object.
        CustomerPricing customerPricing = customerPricingResponse.body();
        return customerPricing;
    }

    public EtaRequest getETAfromS4() throws IOException {
        String fullURL = "Customer eq '1006707' and Material eq '03476500000.CONT' and OrderQty eq '50' and RequestDate eq datetime'2022-02-18T00:00:00' and Studded eq false and ShippingMethod eq '01' and AcceptSaturday eq true";
        String csrfToken = "";
        EtaService service2 = Client.getRetrofitInstance("https://tch-s4hds1.grtouchette.com:44300/", "kwang", "Kabin321!!!").
                create(EtaService.class);
        Call<ResponseBody> call = service2.getEtaCSRFToken();

        Response<ResponseBody> response = call.execute();
        //get x-csrf-token header
        Headers headers = response.headers();
        for (String name : headers.toMultimap().keySet()) {
            if (name != null && "x-csrf-token".equalsIgnoreCase(name)) {
                csrfToken = headers.get(name);
                break;
            }
        }
        //check x-csrf-token is empty or not
        if (csrfToken.isEmpty()) {
            System.out.println("Didn't receive a x-csrf-token! Exit abnormal!");
            System.exit(1);
        }
        //get response' cookie set
        List<String> cookieList = response.headers().values("set-cookie");

        EtaService service3 = Client.getRetrofitInstance("https://tch-s4hds1.grtouchette.com:44300/", "kwang", "Kabin321!!!").
                create(EtaService.class);

        Call<EtaRequest> callJson = service3.getEtaGson(fullURL, csrfToken, cookieList);

        Response<EtaRequest> etaRequestResponse = callJson.execute();
        if (etaRequestResponse.isSuccessful()) {
            System.out.println("Retrieve ETA from S4 API is successful");
        } else {
            System.out.println("Retrieve ETA from S4 API failed! Exit abnormal!");
            System.exit(1);
        }
        //Got Response body in JSON and converted to POJO object.
        EtaRequest etaRequest = etaRequestResponse.body();
        return etaRequest;
    }

    }

