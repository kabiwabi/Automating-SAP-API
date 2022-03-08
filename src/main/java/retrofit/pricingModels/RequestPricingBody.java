package retrofit.pricingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPricingBody {
    @SerializedName("CustomerNumber")
    @Expose
    private String customerNumber;
    @SerializedName("NoEmptyLiquidationPrice")
    @Expose
    private Boolean noEmptyLiquidationPrice;
    @SerializedName("SortByQty")
    @Expose
    private Boolean sortByQty;
    @SerializedName("PriceListTypes")
    @Expose
    private RequestPriceListTypes priceListTypes;
    @SerializedName("MaterialsList")
    @Expose
    private RequestMaterialsList materialsList;

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Boolean getNoEmptyLiquidationPrice() {
        return noEmptyLiquidationPrice;
    }

    public void setNoEmptyLiquidationPrice(Boolean noEmptyLiquidationPrice) {
        this.noEmptyLiquidationPrice = noEmptyLiquidationPrice;
    }

    public Boolean getSortByQty() {
        return sortByQty;
    }

    public void setSortByQty(Boolean sortByQty) {
        this.sortByQty = sortByQty;
    }

    public RequestPriceListTypes getPriceListTypes() {
        return priceListTypes;
    }

    public void setPriceListTypes(RequestPriceListTypes priceListTypes) {
        this.priceListTypes = priceListTypes;
    }

    public RequestMaterialsList getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(RequestMaterialsList materialsList) {
        this.materialsList = materialsList;
    }

}
