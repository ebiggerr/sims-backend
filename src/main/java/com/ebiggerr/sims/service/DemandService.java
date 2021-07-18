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

import com.ebiggerr.sims.domain.demand.Demand;
import com.ebiggerr.sims.domain.stock.Stock;
import com.ebiggerr.sims.domain.stock.StockDTO;
import com.ebiggerr.sims.repository.DemandRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ebiggerr.sims.service.DoubleExponentialSmoothing.DemandForecasting.exponentialSmoothing_TimeSeries;

@Service
public class DemandService {

    private final DemandRepo demandRepo;

    private final double Double_THREE_TIME_UNIT=3.0;
    private final int Int_THREE_TIME_UNIT=3;

    public DemandService(DemandRepo demandRepo){
        this.demandRepo= demandRepo;
    }

    /**
     * <h1> Get the demand forecast for an item with given item id</h1>
     * @param itemID id of the item
     * @return [List] of demand for that item
     */
    public List<Demand> getDemandForestingFor3MonthsTimeUnit(long itemID){

        //double array to contain the historical sales number for past THREE_TIME_UNIT
        double[] data= new double[(int) (Double_THREE_TIME_UNIT) ];

        //double array to contain the historical sales number + possible sales number (demand forecasting) for coming THREE_TIME_UNIT
        double[] resultArr= new double[(int) (Double_THREE_TIME_UNIT + Double_THREE_TIME_UNIT) ];
        Optional<List<Demand>> tempContain= Optional.empty();

        try {
            //get the historical sales data if there is any, for that item
            tempContain = demandRepo.getHistoricalSales3MonthsB(itemID);

        }catch (NullPointerException e){
            return null;
        }

        if( tempContain.isEmpty() ){
            return null;
        }

        List<Demand> tempList = null;

        if( tempContain.isPresent() ){
            tempList = tempContain.get();

            if( tempList.size() == 0 ){
                return null;
            }
        }

        String monthIndex = null;
        int tempforMonthIndex=0;

        int index=0;
        while( index < tempList.size() ){

            //load the sales number into the array (int cast to double)
            data[index] = tempList.get(index).getValue();
            //update the month index to the greatest available in the result return from the database
            monthIndex=tempList.get(index).getMonthInNumber();
            index++;
        }
        index=0;
        tempforMonthIndex= Integer.parseInt(monthIndex);

        double[] temp =exponentialSmoothing_TimeSeries(data, Int_THREE_TIME_UNIT );

        //concat the array from database and the array from demand forecasting
        System.arraycopy(data,0,resultArr,0, data.length );
        System.arraycopy(temp,0,resultArr, Int_THREE_TIME_UNIT, temp.length );


        while( (index+ Int_THREE_TIME_UNIT) < resultArr.length ){

            ++tempforMonthIndex; // add the month index ( so that the we can know the month of the forecast value)
            //add new demand, with forecast demand and month
            tempList.add( new Demand( String.valueOf( (tempforMonthIndex) ), resultArr[index+ Int_THREE_TIME_UNIT ] ) );
            index++;
        }

        return tempList;
    }

    public StockDTO populateDTOUsingDemandService(Stock stock) {

        StockDTO dto =new StockDTO();

        dto.setItemID( stock.getItemID() );
        dto.setItemName( stock.getItemName() );
        dto.setSkuNumber( stock.getSkuNumber() );
        dto.setQuantity( stock.getQuantity() );
        dto.setDemandForecast( getDemandForestingFor3MonthsTimeUnit( stock.getItemID() ) ); //have to use the method of getting demand forecast
        dto.setLastRestockDate( stock.getLastRestockDate() );

        return dto;
    }
}
