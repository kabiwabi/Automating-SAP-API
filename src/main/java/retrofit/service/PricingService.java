package retrofit.service;

import java.util.List;

import okhttp3.*;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Headers;
import retrofit.model.pricingModels.RequestPricingBody;
import retrofit.model.pricingModels.CustomerPricing;

public interface PricingService {

    @Headers({
            "Accept: application/json",
            "X-CSRF-Token: Fetch"
    })
    @GET("sap/opu/odata/sap/ZSD_CUSTOMER_PRICING_SRV/CustomerSet?sap-client=110")
    Call<ResponseBody> getPricingCSRFToken();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("sap/opu/odata/sap/ZSD_CUSTOMER_PRICING_SRV/CustomerSet?sap-client=110")
    Call<CustomerPricing> postPricingSearchGson(
            @Body RequestPricingBody requestBodyGson,@Header("x-csrf-token") String csrfToken, @Header("cookie") List<String> cookieList
    );
}
