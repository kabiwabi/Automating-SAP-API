package retrofit.pricingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestMaterialsListResult {
    @SerializedName("Material")
    @Expose
    private String material;

    public RequestMaterialsListResult(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
