package retrofit;

import java.io.IOException;
import java.util.List;

import okhttp3.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Service {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("PLACEHOLDER/PLACEHOLDER/PLACEHOLDER")
    Call<ResponseBody> postPricingSearch(
            @Body String body, @Header("x-csrf-token") String csrfToken, @Header("cookie") List<String> cookieList
    );

}
