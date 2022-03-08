package com.ondemand.commerce.pages;

import com.ondemand.commerce.pages.data.Tire;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import retrofit.etaModels.*;
import retrofit.model.pricingModels.*;
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
    public List getLocatorList(By locator) {
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

    public CustomerPricing getPricingFromS4()  throws IOException {
        String csrfToken = "";
        PricingService service = Client.getRetrofitInstance("http://tch-s4hds1.grtouchette.com:8000/","kwang","Kabin321!!!")
                .create(PricingService.class);
        Call<ResponseBody> call = service.getPricingCSRFToken();
        /**
         * NOTE: when we call the API, it will always response error:501 not implemented.
         *       It is normal. We just call the API to get x-csrf-token and cookies for next Post method.
         *       So we don't check response is successful or not.
         */
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
        if(csrfToken.isEmpty()) {
            System.out.println("Didn't receive a x-csrf-token! Exit abnormal!");
            System.exit(1);
        }
        //get response' cookie set
        List<String> cookieList = response.headers().values("set-cookie");

        /**
         * call post method of Pricing API to get response of searching price.
         *
         */
        //Prepare Body of request in JSON
        RequestPricingBody requestPricingBody = initialRequestBodyJson();
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

    public EtaRequest getETAfromS4()  throws IOException {
        String fullURL = "Customer eq '1006707' and Material eq '03476500000.CONT' and OrderQty eq '50' and RequestDate eq datetime'2022-02-18T00:00:00' and Studded eq false and ShippingMethod eq '01' and AcceptSaturday eq true";
        String csrfToken = "";
        EtaService service2 = Client.getRetrofitInstance("https://tch-s4hds1.grtouchette.com:44300/","kwang","Kabin321!!!").
                create(EtaService.class);
        Call<ResponseBody> call = service2.getEtaCSRFToken();
        /**
         * NOTE: when we call the API, it will always response error:501 not implemented.
         *       It is normal. We just call the API to get x-csrf-token and cookies for next Post method.
         *       So we don't check response is successful or not.
         */
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
        if(csrfToken.isEmpty()) {
            System.out.println("Didn't receive a x-csrf-token! Exit abnormal!");
            System.exit(1);
        }
        //get response' cookie set
        List<String> cookieList = response.headers().values("set-cookie");

        /**
         * call post method of Pricing API to get response of searching ETA.
         *
         */
        EtaService service3 = Client.getRetrofitInstance("https://tch-s4hds1.grtouchette.com:44300/","kwang","Kabin321!!!").
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

    /**
     * Return a request body POJO object with the required JSON structure in the document of Pricing API
     * Data source is from the instance variables which are set through method readConfigurationXML().
     *
     * @return a new RequestPricingBody
     */
    private RequestPricingBody initialRequestBodyJson() {
        readConfigurationXML();
        List<RequestPriceListTypesResult> requestPriceListTypesResults = new ArrayList<RequestPriceListTypesResult>();
        //Iterating the ArrayList of xmlPriceListTypes using Lambda Expression
        xmlPriceListTypes.forEach( (n) -> {
            requestPriceListTypesResults.add(new RequestPriceListTypesResult(n));
        });
        RequestPriceListTypes requestPriceListTypes= new RequestPriceListTypes();
        requestPriceListTypes.setResults(requestPriceListTypesResults);
        List<RequestMaterialsListResult> requestMaterialsListResults = new ArrayList<RequestMaterialsListResult>();
        //Iterating the ArrayList of xmlMaterialsList
        //Use Java's Consumer interface to store a lambda expression in a variable:
        Consumer<String> method = (n) -> { requestMaterialsListResults.add(new RequestMaterialsListResult(n)); };
        xmlMaterialsList.forEach(method);
        RequestMaterialsList requestMaterialsList= new RequestMaterialsList();
        requestMaterialsList.setResults(requestMaterialsListResults);
        RequestPricingBody requestPricingBody = new RequestPricingBody();
        requestPricingBody.setCustomerNumber(customerNumber);
        requestPricingBody.setMaterialsList(requestMaterialsList);
        requestPricingBody.setPriceListTypes(requestPriceListTypes);
        requestPricingBody.setNoEmptyLiquidationPrice(false);
        requestPricingBody.setSortByQty(true);
        return requestPricingBody;
    }

    private void readConfigurationXML() {
        SAXReader reader = new SAXReader();
        Document document = null;
        //begin for developing and debug. For runnable jar file version, please comment it.
        FILENAME = "C:\\Users\\kwang\\Documents\\TouchetteAutomation\\configurationWindows.xml.tld";
        //end of for developing and debug.
        try {
            document = reader.read(new File(FILENAME));
        } catch (DocumentException e) {
            System.out.println("Can't open " + FILENAME +" ! Please check there is the file in current folder.");
            System.out.println(e);
            System.exit(1);
        }
        // Get baseURL
        Node node = document.selectSingleNode("//baseURL"); //using XPath to get xml node
        if (node == null) { // doesn't find baseURL in the xml file
            System.out.println("Can't find xml node: <baseURL></baseURL> in configuartion xml! ");
            System.exit(1);
        } else {
            baseURL = node.getStringValue();
        }
        // Get webdriverHeadless
        node = document.selectSingleNode("//webdriverHeadless");
        if (node == null) {
            System.out.println("Can't find xml node: <webdriverHeadless></webdriverHeadless> in configuartion xml! ");
            System.exit(1);
        } else {
            webdriverHeadless = node.getStringValue();
        }
        // Get Chrome driver path
        node = document.selectSingleNode("//chromeDriver");
        if (node == null) {
            System.out.println("Can't find xml node: <chromeDriver></chromeDriver> in configuartion xml! ");
            System.exit(1);
        } else {
            driverPath = node.getStringValue();
        }
        // Get S4 baseURL
        node = document.selectSingleNode("//s4BaseURL");
        if (node == null) {
            System.out.println("Can't find xml node: <s4BaseURL></s4BaseURL> in configuartion xml! ");
            System.exit(1);
        } else {
            s4BaseURL = node.getStringValue();
        }
        // Get S4 Authorization's User Name
        node = document.selectSingleNode("//s4AuthorizationName");
        if (node == null) {
            System.out.println("Can't find xml node: <s4AuthorizationName></s4AuthorizationName> in configuartion xml! ");
            System.exit(1);
        } else {
            s4AuthorizationName = node.getStringValue();
        }
        // Get S4 Authorization's User Password
        node = document.selectSingleNode("//s4AuthorizationPassword");
        if (node == null) {
            System.out.println("Can't find xml node: <s4AuthorizationPassword></s4AuthorizationPassword> in configuartion xml! ");
            System.exit(1);
        } else {
            s4AuthorizationPassword = node.getStringValue();
        }
        // Get Test data
        node = document.selectSingleNode("//testCase[@id=\"case004\"]/asmUserName");
        if (node == null) {
            System.out.println("Can't find xml node: <testCase id=\"case004\"><asmUserName></asmUserName></testCase> in configuartion xml! ");
            System.exit(1);
        } else {
            asmUserName = node.getStringValue();
        }
        node = document.selectSingleNode("//testCase[@id=\"case004\"]/password");
        if (node == null) {
            System.out.println("Can't find xml node: <testCase id=\"case004\"><password></password></testCase> in configuartion xml! ");
            System.exit(1);
        } else {
            password = node.getStringValue();
        }
        node = document.selectSingleNode("//testCase[@id=\"case004\"]/customerNumber");
        if (node == null) {
            System.out.println("Can't find xml node: <testCase id=\"case004\"><customerNumber></customerNumber></testCase> in configuartion xml! ");
            System.exit(1);
        } else {
            customerNumber = node.getStringValue();
        }
        node = document.selectSingleNode("//testCase[@id=\"case004\"]/customerName");
        if (node == null) {
            System.out.println("Can't find xml node: <testCase id=\"case004\"><customerName></customerName></testCase> in configuartion xml! ");
            System.exit(1);
        } else {
            customerName = node.getStringValue();
        }
        // Get Test data for S4 Restful Pricing API request body: priceListTypes
        List<Node> listNodes = document.selectNodes("//testCase[@id=\"case004\"]/priceListTypes/type");
        if (listNodes == null) {
            System.out.println("Can't find xml node: <testCase id=\"case004\"><priceListTypes><type></type></priceListTypes></testCase> in configuartion xml! ");
            System.exit(1);
        } else {
            for (Iterator<Node> iter = listNodes.iterator(); iter.hasNext();) {
                node = iter.next();
                String typeInS4API = node.getStringValue();
                xmlPriceListTypes.add(typeInS4API);
            }
        }
        // Get Test data for S4 Restful Pricing API request body: materialsList
        listNodes = document.selectNodes("//testCase[@id=\"case004\"]/materialsList/material");
        if (listNodes == null) {
            System.out.println("Can't find xml node: <testCase id=\"case004\"><materialsList><material></material></materialsList></testCase> in configuartion xml! ");
            System.exit(1);
        } else {
            for (Iterator<Node> iter = listNodes.iterator(); iter.hasNext();) {
                node = iter.next();
                String materialInS4API = node.getStringValue();
                xmlMaterialsList.add(materialInS4API);
            }
        }
    }

}
