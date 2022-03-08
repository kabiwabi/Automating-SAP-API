package retrofit.etaModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D2 {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getEtaResults() {
        return results;
    }

    public void setEtaResults(List<Result> results) {
        this.results = results;
    }

}