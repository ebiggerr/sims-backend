package com.ebiggerr.sims.domain.inventory;


import javax.persistence.*;

@Entity
@Table(name="itemlisting")
public class item {

    @Id
    @Column(name="itemid")
    private long id;

    @Column(name="skunumber")
    private String SKU;

    @Column(name="imagepathfull")
    private String imagePath;

    @Column(name="itemname")
    private String itemName;

    @Column(name="itemdimension",nullable = true)
    private String dimensions;

    @Column(name="itemvolume",nullable = true)
    private String volume;

    @Column(name="itemdescription")
    private String itemDescription;

    @Column(name="itemunitprice")
    private double unitPrice;

    @Column(name = "categoryid")
    private long categoryID;

    public item(){

    }

    public item(long id, String SKU, String imagePath, String itemName, String dimensions, String volume, String itemDescription, double unitPrice, long categoryID) {
        this.id = id;
        this.SKU = SKU;
        this.imagePath = imagePath;
        this.itemName = itemName;
        this.dimensions = dimensions;
        this.volume = volume;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.categoryID = categoryID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getCategoryID() { return categoryID; }

    public void setCategoryID(long categoryID) { this.categoryID = categoryID; }
}

