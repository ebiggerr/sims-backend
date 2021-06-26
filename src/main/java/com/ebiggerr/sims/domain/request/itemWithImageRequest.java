package com.ebiggerr.sims.domain.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class itemWithImageRequest {

    @NotNull
    private MultipartFile image;

    @NotNull
    @NotBlank
    @Size(min=4)
    private String skuNumber;

    private String imagePath;

    @NotNull
    @NotBlank
    @Size(min=4)
    private String itemName;

    @NotNull
    @NotBlank
    private String dimensions;

    @NotNull
    @NotBlank
    private String volume;

    @NotNull
    @NotBlank
    @Size(min=4)
    private String itemDescription;

    @NotNull
    @NotBlank
    private String unitPrice;

    @NotBlank
    private String categoryName;

    public itemWithImageRequest(MultipartFile image,String skuNumber, String imagePath, String itemName, String dimensions, String volume, String itemDescription, String unitPrice, String categoryName) {
        this.image=image;
        //this.id = id;
        this.skuNumber = skuNumber;
        this.imagePath = imagePath;
        this.itemName = itemName;
        this.dimensions = dimensions;
        this.volume = volume;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.categoryName=categoryName;
    }

    public MultipartFile getImage(){
        return this.image;
    }

    public void setImage(MultipartFile image){
        this.image=image;
    }

   /* public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/

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

    public String getCategoryName(){ return categoryName; }

    public void setCategoryName(String categoryName){ this.categoryName = categoryName; }


}
