package retrofit.pricingModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestMaterialsList {
    @SerializedName("results")
    @Expose
    private List<RequestMaterialsListResult> results = null;

    public List<RequestMaterialsListResult> getResults() {
        return results;
    }

    public void setResults(List<RequestMaterialsListResult> results) {
        this.results = results;
    }
}
