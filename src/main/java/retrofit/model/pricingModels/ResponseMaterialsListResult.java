package retrofit.model.pricingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMaterialsListResult {

    @SerializedName("__metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("Material")
    @Expose
    private String material;
    @SerializedName("PriceListType")
    @Expose
    private String priceListType;
    @SerializedName("AFSQuantity")
    @Expose
    private String aFSQuantity;
    @SerializedName("ADDQuantity")
    @Expose
    private String aDDQuantity;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("IsDefault")
    @Expose
    private Boolean isDefault;
    @SerializedName("EcoFee")
    @Expose
    private String ecoFee;
    @SerializedName("LiquidationPrice")
    @Expose
    private String liquidationPrice;
    @SerializedName("BookingId")
    @Expose
    private String bookingId;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPriceListType() {
        return priceListType;
    }

    public void setPriceListType(String priceListType) {
        this.priceListType = priceListType;
    }

    public String getAFSQuantity() {
        return aFSQuantity;
    }

    public void setAFSQuantity(String aFSQuantity) {
        this.aFSQuantity = aFSQuantity;
    }

    public String getADDQuantity() {
        return aDDQuantity;
    }

    public void setADDQuantity(String aDDQuantity) {
        this.aDDQuantity = aDDQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getEcoFee() {
        return ecoFee;
    }

    public void setEcoFee(String ecoFee) {
        this.ecoFee = ecoFee;
    }

    public String getLiquidationPrice() {
        return liquidationPrice;
    }

    public void setLiquidationPrice(String liquidationPrice) {
        this.liquidationPrice = liquidationPrice;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

}
