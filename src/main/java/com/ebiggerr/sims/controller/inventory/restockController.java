package com.ebiggerr.sims.controller.inventory;

import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.service.stock.stockService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class restockController {

    private final stockService stockService;

    public restockController(stockService stockService){
        this.stockService=stockService;
    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager','Staff')")
    @GetMapping(path = "/inventory/stock/all")
    public API_Response allStockInfo(){

        return new API_Response().Success( stockService.getAll() );

    }

    /*@PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @GetMapping(path = "/inventory/stock/demand/all")
    public API_Response allStockInfoWithDemand(){

        return new API_Response().Success( stockService.getAllDemandForecastAndStockInfo() );

    }*/

    /*@PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @GetMapping(path = "/inventory/stock/demand/{skunumber}")
    public API_Response viewAStockWithDemand(@PathVariable String skunumber){

        return new API_Response().Success( *//*stockService.getAll()*//* );

    }*/

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @PutMapping(path = "/inventory/restock")
    public API_Response restock(@RequestParam(name ="skunumber")String skunumber, @RequestParam(name ="quantity")int quantity ){

        boolean success= stockService.restockWithGivenQuantity(skunumber,quantity);

        if( success) return new API_Response().Success();

        return new API_Response().Error("Something Went Wrong");

    }

    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    @PutMapping(path = "/inventory/addStock")
    public API_Response addStockForItem(@RequestParam(name ="skunumber")String skunumber, @RequestParam(name ="quantity")int quantity ){

        boolean success= stockService.AddStockForAnItem(skunumber,quantity);

        if( success) return new API_Response().Success();

        return new API_Response().Error("Something Went Wrong");

    }



}
