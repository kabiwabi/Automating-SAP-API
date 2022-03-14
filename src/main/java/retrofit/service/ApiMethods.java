package retrofit.service;

import com.ondemand.commerce.pages.SearchResultsPage;
import com.ondemand.commerce.pages.data.Tire;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import retrofit.etaModels.*;
import retrofit.pricingModels.*;
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



public class ApiMethods{

    private static String baseURL;
    private static String driverPath;
    private WebDriver driver;
    private static String asmUserName;
    private static String password;
    private static String customerNumber ="0001006563";
    private static String customerName;
    private static String webdriverHeadless;
    private static String s4BaseURL;
    private static String s4AuthorizationName;
    private static String s4AuthorizationPassword;
    private static List<String> xmlPriceListTypes = new ArrayList<String>();
    private static List<String> xmlMaterialsList = new ArrayList<String>();
    public static String FILENAME = "";



}
