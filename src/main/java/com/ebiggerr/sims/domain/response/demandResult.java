package com.ebiggerr.sims.domain.response;

import com.ebiggerr.sims.domain.demand.demand;
import com.ebiggerr.sims.domain.inventory.itemDTO;
import com.ebiggerr.sims.domain.stock.stock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class demandResult implements Serializable {

    private LocalDateTime now;
    private itemDTO item;
    private stock stockInfo;
    private List<demand> demandForecast;

    public demandResult(LocalDateTime now, itemDTO item, stock stockInfo, List<demand> demandForecast) {
        this.now = now;
        this.item = item;
        this.stockInfo=stockInfo;
        this.demandForecast = demandForecast;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }

    public itemDTO getItem() {
        return item;
    }

    public void setItem(itemDTO item) {
        this.item = item;
    }

    public stock getStockInfo(){
        return stockInfo;
    }

    public void setStockInfo(stock stockInfo){
        this.stockInfo=stockInfo;
    }

    public List<demand> getDemandForecast() {
        return demandForecast;
    }

    public void setDemandForecast(List<demand> demandForecast) {
        this.demandForecast = demandForecast;
    }
}
