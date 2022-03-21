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
import retrofit.service.Client;
import retrofit.service.EtaService;
import retrofit.service.PricingService;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
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
    private By clientPriceLocator = By.xpath("//dl[@class='search-results__pdm-content search-results__pdm-prices']//dt[contains(text(), 'P')]/following-sibling::dt[contains(text(), 'D')]/preceding-sibling::dd");
    private By accountNumberLocator = By.xpath("//span[@class='nav__account-number']");
    private By seasonTagLocator = By.xpath("//span[@class='c-season__label']");
    private By allSeasonCheckboxLocator = By.xpath("/html[1]/body[1]/main[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/ul[1]/li[1]/form[1]/label[1]/span[1]/span[1]");
    private By allWeatherCheckboxLocator = By.xpath("/html[1]/body[1]/main[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/ul[1]/li[2]/form[1]/label[1]/span[1]/span[1]");
    private By summerCheckboxLocator = By.xpath("/html[1]/body[1]/main[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/ul[1]/li[3]/form[1]/label[1]/span[1]/span[1]");

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

    public void clickAllSeason() {
        log.info("Clicking the all-season checkbox");
        click(allSeasonCheckboxLocator);
    }

    public void clickAllWeather() {
        log.info("Clicking the all-weather checkbox");
        click(allWeatherCheckboxLocator);
    }

    public void clickSummer() {
        log.info("Clicking the summer checkbox");
        click(summerCheckboxLocator);
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
        for (int i=0; i<tempList.size(); i++) {
            tempList.set(i,parseCustomerPrice(tempList.get(i)));
        }
        return tempList;
    }

    public  List getSeasonList() {
        List<String> tempList = getLocatorList(seasonTagLocator);
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
    public void assignAllTire(List<String> completeSkuList, List<String> tireNameList, List<String> msrpList,
                              List<String> suggPriceList, List<String> localAvailList,
                              List<String> warehouseAvailList, List<String> etaList, List<String> clientPriceList, List<String> seasonList, List<Tire> tireList) {

        int counter = 0;
        for (Tire temp : tireList) {
            temp.setSku(completeSkuList.get(counter));
            temp.setTireName(tireNameList.get(counter));
            temp.setMsrp(msrpList.get(counter));
            temp.setSuggestedPrice(suggPriceList.get(counter));

            if (warehouseAvailList.size() < completeSkuList.size()) {
                if (counter>=warehouseAvailList.size()) {
                    temp.setWarehouseAvailability("Not available");
                }
                else{
                temp.setWarehouseAvailability(localAvailList.get(counter));}
            }
            else {
                temp.setWarehouseAvailability(localAvailList.get(counter));
            }

            if (localAvailList.size() < completeSkuList.size()) {
                if (counter>=localAvailList.size()) {
                    temp.setLocalAvailability("Not available");
                }
                else{
                    temp.setLocalAvailability(localAvailList.get(counter));}
            }
            else {
                temp.setLocalAvailability(localAvailList.get(counter));
            }

            temp.setEta(etaList.get(counter));
            temp.setClientPrice(clientPriceList.get(counter));
            temp.setSeasonTag((seasonList.get(counter)));
            counter++;
        }
    }

    public List<Tire> initListAssignAllTires() {
        List<Tire> tireList = new ArrayList<>();
        initializeTireList(getSkuList(), tireList);
        assignAllTire(getCompleteSkuList(), getTireNameList(), getMSRPList(), getSuggPriceList(), getLocalList(), getWarehouseList(), getEtaList(), getClientPriceList(), getSeasonList(), tireList);
        return tireList;
    }

    public void printAllTires(List<Tire> tireList) {
        tireList.stream().forEach(System.out::println);
    }

    public void printFromList(List<String> myList) {
        myList.stream().forEach(System.out::println);
    }

    public RequestPricingBody initialRequestBodyJson(Tire tireProduct) {

        List<RequestPriceListTypesResult> requestPriceListTypesResults = new ArrayList<RequestPriceListTypesResult>();
        requestPriceListTypesResults.add(new RequestPriceListTypesResult("ZD"));
        RequestPriceListTypes requestPriceListTypes = new RequestPriceListTypes();
        requestPriceListTypes.setResults(requestPriceListTypesResults);

        List<RequestMaterialsListResult> requestMaterialsListResults = new ArrayList<RequestMaterialsListResult>();
        requestMaterialsListResults.add(new RequestMaterialsListResult(tireProduct.getSku()));
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

    public CustomerPricing getPricingFromS4(Tire tireProduct) throws IOException {
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

        RequestPricingBody requestPricingBody = initialRequestBodyJson(tireProduct);
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

    public EtaRequest getETAfromS4(Tire tireProduct) throws IOException {
//        String fullURL = "Customer eq '1006707' and Material eq '03476500000.CONT' and OrderQty eq '5' and RequestDate eq datetime'2022-03-15T00:00:00' and Studded eq false and ShippingMethod eq '01' and AcceptSaturday eq true";
        String fullURL = buildEtaApiString(tireProduct);
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
            System.out.println("Retrieving the ETA from S4 API was successful");
        } else {
            System.out.println("Retrieving the ETA from S4 API failed!");
            System.exit(1);
        }
        //Got Response body in JSON and converted to POJO object.
        EtaRequest etaRequest = etaRequestResponse.body();
        return etaRequest;
        }

        public String buildEtaApiString(Tire tireProduct){
        String customerNumber = find(accountNumberLocator).getText();
        customerNumber = customerNumber.substring(customerNumber.length() - 10);
        String etaUrlString = "Customer eq '"+customerNumber+"' and Material eq '"+tireProduct.getSku()+"' and OrderQty eq '5' and RequestDate eq datetime'2022-03-15T00:00:00' and Studded eq false and ShippingMethod eq '01' and AcceptSaturday eq true";
        return etaUrlString;
        }

        public String parseCustomerPrice(String customerPrice) {
            String returnedString = "";
            int myIndex = 0;
            for (int i = 0; i < customerPrice.length(); i++) {
                if (customerPrice.charAt(i) != '$') {

                } else if (customerPrice.charAt(i) == '$') {
                    myIndex = i;
                }
            }
            for (int i = myIndex; i < customerPrice.length(); i++) {
                returnedString += customerPrice.charAt(i);
            }
            return returnedString;
        }
    }

