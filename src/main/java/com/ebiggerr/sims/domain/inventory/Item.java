/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ebiggerr.sims.domain.inventory;


import javax.persistence.*;

@Entity
@Table(name="itemlisting")
public class Item {

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

    public Item(){

    }

    public Item(long id, String SKU, String imagePath, String itemName, String dimensions, String volume, String itemDescription, double unitPrice, long categoryID) {
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

