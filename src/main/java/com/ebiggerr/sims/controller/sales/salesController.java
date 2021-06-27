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
