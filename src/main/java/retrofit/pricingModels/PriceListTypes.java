package retrofit.pricingModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceListTypes {

    @SerializedName("results")
    @Expose
    private List<ResponsePriceListTypesResult> results = null;

    public List<ResponsePriceListTypesResult> getResults() {
        return results;
    }

    public void setResults(List<ResponsePriceListTypesResult> results) {
        this.results = results;
    }

}