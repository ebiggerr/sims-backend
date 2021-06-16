package com.ebiggerr.sims.domain.request;

public class itemRequest {

    private long id;
    private String skuNumber;
    private String imagePath;
    private String itemName;
    private String dimensions;
    private String volume;
    private String itemDescription;
    private String unitPrice;

    public itemRequest(long id,String skuNumber, String imagePath, String itemName, String dimensions, String volume, String itemDescription, String unitPrice) {
        this.id = id;
        this.skuNumber = skuNumber;
        this.imagePath = imagePath;
        this.itemName = itemName;
        this.dimensions = dimensions;
        this.volume = volume;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSKU() {
        return skuNumber;
    }

    public void setSKU(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }




}
