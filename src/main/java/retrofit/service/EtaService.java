package retrofit.service;

import java.util.List;

import okhttp3.*;
import retrofit.etaModels.EtaRequest;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.Headers;

public interface EtaService {

    @Headers({"Accept: application/json", "X-CSRF-Token: Fetch"})
    @GET("sap/opu/odata/sap/ZSD_CUSTOMER_PRICING_SRV/CustomerSet?sap-client=110")
    Call<ResponseBody> getEtaCSRFToken();

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @GET("/sap/opu/odata/sap/ZLE_SOURCING_LOGIC_SRV_SRV/UIShipmentSet")
    Call<EtaRequest> getEtaGson(
            @Query("$filter") String urlPath, @Header("x-csrf-token") String csrfToken, @Header("cookie") List<String> cookieList
    );
}
