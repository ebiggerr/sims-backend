package com.ebiggerr.sims.domain.inventory;


import javax.persistence.*;

@Entity
@Table(name="itemlisting")
public class item {

    @Id
    @Column(name="itemid")
    private long id;

    @Column(name="itemsku")
    private String SKU;

    @Column(name="imagepath")
    private String imagePath;

    @Column(name="itemname")
    private String itemName;

    @Column(name="itemdimensions")
    private String dimensions;

    @Column(name="itemvolume")
    private String volume;

    @Column(name="itemdescription")
    private String itemDescription;

    @Column(name="itemunitprice")
    private String unitPrice;

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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
