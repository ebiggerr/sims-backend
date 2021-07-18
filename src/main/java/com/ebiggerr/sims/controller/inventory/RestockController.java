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

import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.service.stock.StockService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestockController {

    private final StockService stockService;

    public RestockController(StockService stockService){
        this.stockService=stockService;
    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path = "/inventory/stock/all")
    public API_Response getAllStockInfo(){

        return new API_Response().Success( stockService.getAll() );

    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @PutMapping(path = "/inventory/restock")
    public API_Response restockAnItemWithGivenSKUAndQuantity(@RequestParam(name ="skunumber")String skunumber, @RequestParam(name ="quantity")int quantity ){

        boolean success= stockService.restockWithGivenQuantity(skunumber,quantity);

        if( success) return new API_Response().Success();

        return new API_Response().Error("Something Went Wrong");

    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @PutMapping(path = "/inventory/addStock")
    public API_Response addStockForItemWithGivenSKUAndQuantity(@RequestParam(name ="skunumber")String skunumber, @RequestParam(name ="quantity")int quantity ){

        boolean success= stockService.addStockForAnItem(skunumber,quantity);

        if( success) return new API_Response().Success();

        return new API_Response().Error("Something Went Wrong");

    }



}
