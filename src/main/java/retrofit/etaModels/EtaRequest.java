package retrofit.etaModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EtaRequest {

    @SerializedName("d")
    @Expose
    private D2 d2;

    public D2 getD2() {
        return d2;
    }

    public void setD2(D2 d2) {
        this.d2 = d2;
    }

}