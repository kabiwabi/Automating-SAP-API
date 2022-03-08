package retrofit.pricingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePriceListTypesResult {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("Type")
    @Expose
    private String type;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
