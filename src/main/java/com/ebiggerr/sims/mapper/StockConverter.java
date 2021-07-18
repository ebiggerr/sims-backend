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

package com.ebiggerr.sims.mapper;

import com.ebiggerr.sims.domain.stock.Stock;
import com.ebiggerr.sims.domain.stock.StockDTO;
import com.ebiggerr.sims.service.DemandService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockConverter {

    private final DemandService demandService;

    public StockConverter(DemandService demandService) {
        this.demandService = demandService;
    }

    //TODO 08 June 2021
    public StockDTO entityToDTO(Stock stock){

        return demandService.populateDTOUsingDemandService(stock);
    }

    //TODO 08 June 2021
    public List<StockDTO> entitiesToDTO(List<Stock> stockList){

        return stockList.stream().map(this::entityToDTO).collect(Collectors.toList());

    }

}
