package retrofit.etaModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("ConfirmedQty")
    @Expose
    private String confirmedQty;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Material")
    @Expose
    private String material;
    @SerializedName("RequestDate")
    @Expose
    private String requestDate;
    @SerializedName("Plant")
    @Expose
    private String plant;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("OrderQty")
    @Expose
    private String orderQty;
    @SerializedName("DeliveryTime")
    @Expose
    private String deliveryTime;
    @SerializedName("ShippingMethod")
    @Expose
    private String shippingMethod;
    @SerializedName("ShuttleFlag")
    @Expose
    private Boolean shuttleFlag;
    @SerializedName("ShippingFees")
    @Expose
    private String shippingFees;
    @SerializedName("Studded")
    @Expose
    private Boolean studded;
    @SerializedName("AcceptSaturday")
    @Expose
    private Boolean acceptSaturday;
    @SerializedName("CustomerCluster")
    @Expose
    private String customerCluster;
    @SerializedName("UIServiceLevelSet")
    @Expose
    private UIServiceLevelSet uIServiceLevelSet;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getConfirmedQty() {
        return confirmedQty;
    }

    public void setConfirmedQty(String confirmedQty) {
        this.confirmedQty = confirmedQty;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Boolean getShuttleFlag() {
        return shuttleFlag;
    }

    public void setShuttleFlag(Boolean shuttleFlag) {
        this.shuttleFlag = shuttleFlag;
    }

    public String getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(String shippingFees) {
        this.shippingFees = shippingFees;
    }

    public Boolean getStudded() {
        return studded;
    }

    public void setStudded(Boolean studded) {
        this.studded = studded;
    }

    public Boolean getAcceptSaturday() {
        return acceptSaturday;
    }

    public void setAcceptSaturday(Boolean acceptSaturday) {
        this.acceptSaturday = acceptSaturday;
    }

    public String getCustomerCluster() {
        return customerCluster;
    }

    public void setCustomerCluster(String customerCluster) {
        this.customerCluster = customerCluster;
    }

    public UIServiceLevelSet getUIServiceLevelSet() {
        return uIServiceLevelSet;
    }

    public void setUIServiceLevelSet(UIServiceLevelSet uIServiceLevelSet) {
        this.uIServiceLevelSet = uIServiceLevelSet;
    }

}
