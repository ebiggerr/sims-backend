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

package com.ebiggerr.sims.service.stock;

import com.ebiggerr.sims.domain.inventory.item;
import com.ebiggerr.sims.domain.stock.stock;
import com.ebiggerr.sims.domain.stock.stockDTO;
import com.ebiggerr.sims.mapper.stockConverter;
import com.ebiggerr.sims.repository.inventoryRepo;
import com.ebiggerr.sims.repository.restockHistoryRepo;
import com.ebiggerr.sims.repository.stockRepo;
import com.ebiggerr.sims.service.demandService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class stockService {

    private final stockRepo stockRepo;
    private final restockHistoryRepo restockHistoryRepo;
    private final inventoryRepo inventoryRepo;

    public stockService(stockRepo stockRepo, restockHistoryRepo restockHistoryRepo,inventoryRepo inventoryRepo/*, demandRepo demandRepo*/){
        this.stockRepo=stockRepo;
        this.restockHistoryRepo=restockHistoryRepo;
        this.inventoryRepo=inventoryRepo;
        //this.demandRepo=demandRepo;
    }

    /**
     * <h1> Restock an item with give sku number and the quantity of the replenishment</h1>
     * @param skuNumber SKU number of the item
     * @param restockQuantity quantity of the replenishment
     * @return status of the operation, successful : true ? false
     */
    public boolean restockWithGivenQuantity(String skuNumber, int restockQuantity){

        Optional<Long> id= stockRepo.getIDFromSKU(skuNumber);

        LocalDateTime now= LocalDateTime.now(); //timestamp of replenishment operation

        try{
            stockRepo.restock(restockQuantity,now, id.get());
            restockHistoryRepo.addNewHistory(id.get() , restockQuantity, now);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * <h1> Add stock count to new item that does not have any stock count yet, handle with restock operation of there is already a stock count for the item</h1>
     * @param skunumber SKU number of the item
     * @param addQuantity quantity of the replenishment
     * @return status of the operation, successful : true ? false
     */
    public boolean AddStockForAnItem(String skunumber, int addQuantity){

        Optional<Long> id= Optional.empty();

        try {

            id= stockRepo.getIDFromSKU(skunumber);


            /*if( id == -1 ) {


                item item = inventoryRepo.getBySKUIs(skunumber);

                LocalDateTime current = LocalDateTime.now();

                stockRepo.addStockForItemIfNone(item.getId(), skunumber, addQuantity, current, 10);
                restockHistoryRepo.addNewHistory(item.getId(), addQuantity, current);
            }

            else{
                return false;
            }*/

        }catch (NullPointerException e ){

        }

        if( id.isEmpty() ){

            Optional<item> getitem = inventoryRepo.getBySKUIs(skunumber);
            item item = null;

            if( getitem.isPresent() ) item = getitem.get();
            //if that item does not even exist in itemlisting
            else{ return false; }

            LocalDateTime current = LocalDateTime.now();

            stockRepo.addStockForItemIfNone(item.getId(), skunumber, addQuantity, current, 10);
            restockHistoryRepo.addNewHistory(item.getId(), addQuantity, current);

            return true;
        }

        else{

            LocalDateTime newTime= LocalDateTime.now();

            stockRepo.restock(addQuantity,newTime, id.get());
            restockHistoryRepo.addNewHistory(id.get() , addQuantity, newTime);

        }

        return true;
    }

    /**
     * <h1> Returns list of all items that have stock count</h1>
     * @return [List] all items that have stock count
     */
    public List<stock> getAll(){

        return stockRepo.getAll();

    }

    /**
     * <h1> Return a record of item( @entity stock ) with given SKU number </h1>
     * @param skunumber SKU number of the item
     * @return [stock] found match from the database.
     */
    public stock getAnStockInfo(String skunumber) {

        return stockRepo.getAnStock(skunumber);
    }

    /*
    //TODO 08 June 2021
    public stockDTO getDemandForecastAndStockInfo(String skunumber){

        stock stock = stockRepo.getAnStock(skunumber);

        return stockConverter.entityToDTO(stock);

    }
    */

    //TODO 08 June 2021

    /**
     * <h1> Return all the items (stock), with the demand forecast info if there is any </h1>
     * @param demandService injected demandService to use the demandService in converter
     * @return [List] of stockDTO with demand forecast info if there is any ( null and the details of the item (@entity stock) if there is not demand forecast info available
     */
    public List<stockDTO> getAllDemandForecastAndStockInfo(demandService demandService){

        stockConverter converter = new stockConverter(demandService);

        return converter.entitiesToDTO( stockRepo.getAll() );
    }
}
