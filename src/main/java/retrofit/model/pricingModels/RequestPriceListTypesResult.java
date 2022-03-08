package retrofit.model.pricingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPriceListTypesResult {
    @SerializedName("Type")
    @Expose
    private String type;

    public RequestPriceListTypesResult(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
