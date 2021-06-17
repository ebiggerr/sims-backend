package com.ebiggerr.sims.domain.stock;

import com.ebiggerr.sims.domain.demand.demand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class stockDTO {

    private long itemID;
    private String skuNumber;
    private long quantity;
    private Double demandForecast;
    private LocalDateTime lastRestockDate;
    private String itemName;

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Double getDemandForecast() {
        return demandForecast;
    }

    //TODO 08 June 2021
    public void setDemandForecast(List<demand> demandForecast) {

        if( demandForecast == null ){
            this.demandForecast = null;
        }else{

            LocalDate now = LocalDate.now();

            for( demand demand: demandForecast){
                if( Integer.parseInt( demand.getMonthInNumber() ) == now.getMonthValue() ){
                    this.demandForecast = demand.getValue();
                }
            }
        }

    }

    public LocalDateTime getLastRestockDate() {
        return lastRestockDate;
    }

    public void setLastRestockDate(LocalDateTime lastRestockDate) {
        this.lastRestockDate = lastRestockDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
