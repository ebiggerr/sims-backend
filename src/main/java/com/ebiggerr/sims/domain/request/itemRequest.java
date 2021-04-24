package com.ebiggerr.sims.domain.request;

public class itemRequest {

    private long id;

    private String SKU;
    private String imagePath;
    private String itemName;
    private String dimensions;
    private String volume;
    private String itemDescription;
    private String unitPrice;

    public itemRequest(long id, String SKU, String imagePath, String itemName, String dimensions, String volume, String itemDescription, String unitPrice) {
        this.id = id;
        this.SKU = SKU;
        this.imagePath = imagePath;
        this.itemName = itemName;
        this.dimensions = dimensions;
        this.volume = volume;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
    }


}
