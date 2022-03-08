package retrofit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;
import retrofit.pricingModels.CustomerPricing;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private List<String> xmlPriceListTypes = new ArrayList<String>();
    private List<String> xmlMaterialsList = new ArrayList<String>();
    private String customerNumber;
    private String customerName;
    private CustomerPricing customerPricing;
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(String baseURL, String authUsername, String authPassword) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder()
                        .header("Authorization",Credentials.basic(authUsername, authPassword));
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
