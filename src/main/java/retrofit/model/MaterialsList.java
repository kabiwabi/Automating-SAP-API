package retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialsList {

    @SerializedName("results")
    @Expose
    private List<ResponseMaterialsListResult> results = null;

    public List<ResponseMaterialsListResult> getResults() {
        return results;
    }

    public void setResults(List<ResponseMaterialsListResult> results) {
        this.results = results;
    }

}
