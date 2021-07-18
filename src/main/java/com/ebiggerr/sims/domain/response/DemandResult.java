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

package com.ebiggerr.sims.domain.response;

import com.ebiggerr.sims.domain.demand.Demand;
import com.ebiggerr.sims.domain.inventory.ItemDTO;
import com.ebiggerr.sims.domain.stock.Stock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class DemandResult implements Serializable {

    private LocalDateTime now;
    private ItemDTO item;
    private Stock stockInfo;
    private List<Demand> demandForecast;

    public DemandResult(LocalDateTime now, ItemDTO item, Stock stockInfo, List<Demand> demandForecast) {
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

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public Stock getStockInfo(){
        return stockInfo;
    }

    public void setStockInfo(Stock stockInfo){
        this.stockInfo=stockInfo;
    }

    public List<Demand> getDemandForecast() {
        return demandForecast;
    }

    public void setDemandForecast(List<Demand> demandForecast) {
        this.demandForecast = demandForecast;
    }
}
