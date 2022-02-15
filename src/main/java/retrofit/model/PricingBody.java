package retrofit.model;

import java.io.IOException;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import okhttp3.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class PricingBody {
    @SerializedName("CustomerNumber")
    @Expose
    private String customerNumber;
    @SerializedName("NoEmptyLiquidationPrice")
    @Expose
    private Boolean noEmptyLiquidationPrice;
    @SerializedName("SortByQty")
    @Expose
    private Boolean sortByQty;
}
