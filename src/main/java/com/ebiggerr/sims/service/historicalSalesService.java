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

package com.ebiggerr.sims.service;

import com.ebiggerr.sims.domain.sales.historicalSales;
import com.ebiggerr.sims.repository.historicalSalesRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class historicalSalesService {

    private final historicalSalesRepo historicalSalesRepo;

    public historicalSalesService( historicalSalesRepo historicalSalesRepo ){
        this.historicalSalesRepo=historicalSalesRepo;

    }

    /**
     * <h1> Return the historical sales grouped by month with given year</h1>
     * @param year target year
     * @return historical sales of the year
     */
    public List<historicalSales> getSalesFromYear(String year){

        String begin = year+ "-01-01"; //begin of the year
        String end = year+ "-12-31"; //end of the year

        LocalDate seriesStartDate = LocalDate.parse(begin);
        LocalDate seriesEndDate = LocalDate.parse(end);

        List<historicalSales> sales= null;

        try{

            sales = historicalSalesRepo.getByMonthsInAYearLocalDate(seriesStartDate,seriesEndDate,seriesStartDate,seriesEndDate);

            if( sales.size() == 0 ){
                return null;
            }

        }catch (Exception e){
            return null;
        }

        return sales;
    }


}
