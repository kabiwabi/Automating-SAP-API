package retrofit.etaModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UIServiceLevelSet {

    @SerializedName("__deferred")
    @Expose
    private Deferred deferred;

    public Deferred getDeferred() {
        return deferred;
    }

    public void setDeferred(Deferred deferred) {
        this.deferred = deferred;
    }

}
