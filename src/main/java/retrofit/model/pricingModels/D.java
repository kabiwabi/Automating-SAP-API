package retrofit.model.pricingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;

    @SerializedName("CustomerNumber")
    @Expose
    private String customerNumber;

    @SerializedName("SortByQty")
    @Expose
    private Boolean sortByQty;

    @SerializedName("NoEmptyLiquidationPrice")
    @Expose
    private Boolean noEmptyLiquidationPrice;

    @SerializedName("SoldTo")
    @Expose
    private String soldTo;

    @SerializedName("MaterialsList")
    @Expose
    private MaterialsList materialsList;

    @SerializedName("PriceListTypes")
    @Expose
    private PriceListTypes priceListTypes;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Boolean getSortByQty() {
        return sortByQty;
    }

    public void setSortByQty(Boolean sortByQty) {
        this.sortByQty = sortByQty;
    }

    public Boolean getNoEmptyLiquidationPrice() {
        return noEmptyLiquidationPrice;
    }

    public void setNoEmptyLiquidationPrice(Boolean noEmptyLiquidationPrice) {
        this.noEmptyLiquidationPrice = noEmptyLiquidationPrice;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public MaterialsList getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(MaterialsList materialsList) {
        this.materialsList = materialsList;
    }

    public PriceListTypes getPriceListTypes() {
        return priceListTypes;
    }

    public void setPriceListTypes(PriceListTypes priceListTypes) {
        this.priceListTypes = priceListTypes;
    }

}