package retrofit.pricingModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPriceListTypes {

    @SerializedName("results")
    @Expose
    private List<RequestPriceListTypesResult> results = null;

    public List<RequestPriceListTypesResult> getResults() {
        return results;
    }

    public void setResults(List<RequestPriceListTypesResult> results) {
        this.results = results;
    }

}

