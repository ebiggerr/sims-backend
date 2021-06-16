package com.ebiggerr.sims.controller.sales;

import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.domain.sales.historicalSales;
import com.ebiggerr.sims.service.historicalSalesService;
import com.ebiggerr.sims.service.input.inputCheckValid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class salesController {

    private final historicalSalesService historicalSalesService;

    public salesController( historicalSalesService historicalSalesService ){
        this.historicalSalesService=historicalSalesService;
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping(path="/sales/{year}")
    public API_Response getSalesForAYear(@PathVariable String year){

        if( !inputCheckValid.checkYear(year) ){
            return new API_Response().Failed("Invalid Request");
        }

        List<historicalSales> list = historicalSalesService.getSalesFromYear(year);

        if( list != null ) return new API_Response().Success( list );
        else {
            return new API_Response().Failed("No Record Found");
        }


    }
}
