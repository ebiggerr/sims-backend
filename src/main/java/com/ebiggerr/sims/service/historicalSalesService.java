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
