
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

package com.ebiggerr.sims.controller.inventory;

import com.ebiggerr.sims.domain.inventory.item;
import com.ebiggerr.sims.domain.inventory.itemDTO;
import com.ebiggerr.sims.domain.request.itemWithImageRequest;
import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.domain.response.demandResult;
import com.ebiggerr.sims.domain.stock.stock;
import com.ebiggerr.sims.service.demandService;
import com.ebiggerr.sims.service.historicalSalesService;
import com.ebiggerr.sims.service.input.inputCheckValid;
import com.ebiggerr.sims.service.inventory.inventoryService;
import com.ebiggerr.sims.service.stock.stockService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class inventoryMainController {

    private final inventoryService inventoryService;

    private final demandService demandService;

    private final stockService stockService;

    private String message;
    private boolean valid;
    private boolean success;


    public inventoryMainController(inventoryService inventoryService,demandService demandService,stockService stockService) {
        this.inventoryService = inventoryService;
        this.demandService=demandService;
        this.stockService=stockService;
    }

    /**
     * <h1> Inventory Listing Sorted by itemID in ascending order</h1>
     * @param pageNumber number of page that want to view
     * @param pageSize size of each page
     * @return List wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path = "/inventory/all" )
    public API_Response getAllFromInventory(@RequestParam(name ="pagenumber")int pageNumber, @RequestParam( name="pagesize")int pageSize){

        //return new API_Response().Success( inventoryService.getItemsPageAndSize(pageNumber,pageSize) );
        return new API_Response().Success( inventoryService.getItemsSortedPageAndSize(pageNumber,pageSize) ); //SORTED by itemID in ascending order
    }

    /**
     * <h1>Inventory Listing by category Sorted by itemID in ascending order </h1>
     * @param pageNumber number of page that want to view
     * @param pageSize size of each page
     * @param categoryName name of category
     * @param categoryID id of category
     * @return List wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path="/inventory/all/categorical")
    public API_Response getFromInventoryByCategory(@RequestParam(name ="pagenumber")int pageNumber, @RequestParam( name="pagesize")int pageSize, @RequestParam(name = "category", required = false) String categoryName, @RequestParam(name="categoryid") String categoryID){

        return new API_Response().Success(
                inventoryService.getItemsByCategoryWithPageAndSize(pageNumber, pageSize, categoryName, categoryID)
        ); //SORTED by itemID in ascending order
    }

    /**
     * <h1> Get all available category in inventory</h1>
     *
     * Information:
     * <br>
     * 1. categoryID
     * <br>
     * 2. categoryName
     *
     * @return List wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path = "/inventory/category/all" )
    public API_Response getAllCategory(){

        return new API_Response().Success(
          inventoryService.getAllCategories()
        );
    }

    /**
     * <h1> </h1>
     * @param SKU sku of the item
     * @return Details of item wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path="/inventory/item/sku/{SKU}")
    public API_Response getAnItemUsingSKUNumber(@PathVariable String SKU){

        itemDTO temp = inventoryService.getItem(SKU);

        if( temp == null ){ return new API_Response().Failed("No Record Found"); }
        return new API_Response().Success( temp );
    }

    /**
     * <h1> </h1>
     * @param ID id of the item
     * @return Details of item wrapped in API_RESPONSE
     */
    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path = "/inventory/item/id/{ID}")
    public API_Response getAnItemUsingID(@PathVariable Long ID){

        itemDTO temp = inventoryService.getItemByID(ID);

        if( temp == null ){ return new API_Response().Failed("No Record Found"); }
        return new API_Response().Success( temp );
    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @DeleteMapping(path = "/inventory/item/{skunumber}")
    public API_Response deleteAnItem(@PathVariable String skunumber){

        success = inventoryService.removeItem( skunumber );

        if(success) return new API_Response().Success();

        return new API_Response().Failed("Something went Wrong Or Item Not Found.");

    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @PutMapping(path ="/inventory/itemWithImage")
    public API_Response updateAnItem( @Valid @ModelAttribute(value = "itemWithImageRequest") itemWithImageRequest item) throws IOException {

        message = inputCheckValid.checkAllForItem(item);
        item updatedItem = null;

        if( message == null){
            updatedItem = inventoryService.updateItemWithImage(item);

            if( updatedItem == null ){
                return new API_Response().Failed("Update Failed.");
            }

        }
        else{
            return new API_Response().Failed(message);
        }

        return new API_Response().Success(updatedItem);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @PostMapping(path ="/inventory/itemWithImage")
    public API_Response addNewItemWithImage( @Valid @ModelAttribute(value = "itemWithImageRequest") itemWithImageRequest item) throws IOException {

        message = inputCheckValid.checkAllForItem(item);

        item response = null;
        if( message == null ){
             response = inventoryService.addNewItemImage(item);
             if( response == null ) return new API_Response().Failed("Duplicates in SKU");

        }

        if( response != null ) return new API_Response().Success(response);

        return new API_Response().Failed(message);
    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @GetMapping(path ="/inventory/demand/{skunumber}")
    public API_Response getDemandForAnItem(@PathVariable String skunumber){

        itemDTO item= inventoryService.getItem(skunumber);
        if( item == null ) return new API_Response().Failed("No Record Found");
        stock stockInfo  = stockService.getAnStockInfo(skunumber);

        //wrapper for result, demandResult: showing time, details of the item and forecast demand
        return new API_Response().Success(
                new demandResult( LocalDateTime.now(),
                        item,
                        stockInfo,
                        demandService.getDemandForestingFor3MonthsTimeUnit( item.getId() ))
        );
    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @GetMapping(path = "/inventory/demand/all")
    public API_Response allStockInfoWithDemand(){

        return new API_Response().Success( stockService.getAllDemandForecastAndStockInfo(demandService) );

    }

}

