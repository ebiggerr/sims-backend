package com.ebiggerr.sims.service;

import com.ebiggerr.sims.domain.demand.demand;
import com.ebiggerr.sims.domain.stock.stock;
import com.ebiggerr.sims.domain.stock.stockDTO;
import com.ebiggerr.sims.repository.demandRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ebiggerr.sims.service.DoubleExponentialSmoothing.demandForecasting.exponentialSmoothing_TimeSeries;

@Service
public class demandService {

    private final demandRepo demandRepo;

    private final double Double_THREE_TIME_UNIT=3.0;
    private final int Int_THREE_TIME_UNIT=3;

    public demandService(demandRepo demandRepo){
        this.demandRepo= demandRepo;
    }

    /**
     * <h1> Get the demand forecast for an item with given item id</h1>
     * @param itemID id of the item
     * @return [List] of demand for that item
     */
    public List<demand> getDemandForestingFor3MonthsTimeUnit(long itemID){

        //double array to contain the historical sales number for past THREE_TIME_UNIT
        double[] data= new double[(int) (Double_THREE_TIME_UNIT) ];

        //double array to contain the historical sales number + possible sales number (demand forecasting) for coming THREE_TIME_UNIT
        double[] resultArr= new double[(int) (Double_THREE_TIME_UNIT + Double_THREE_TIME_UNIT) ];
        Optional<List<demand>> tempContain= Optional.empty();

        try {
            //get the historical sales data if there is any, for that item
            tempContain = demandRepo.getHistoricalSales3MonthsB(itemID);

        }catch (NullPointerException e){
            return null;
        }

        if( tempContain.isEmpty() ){
            return null;
        }

        List<demand> tempList = null;

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
            tempList.add( new demand( String.valueOf( (tempforMonthIndex) ), resultArr[index+ Int_THREE_TIME_UNIT ] ) );
            index++;
        }

        return tempList;
    }

    public stockDTO populateDTOUsingDemandService(stock stock) {

        stockDTO dto =new stockDTO();

        dto.setItemID( stock.getItemID() );
        dto.setItemName( stock.getItemName() );
        dto.setSkuNumber( stock.getSkuNumber() );
        dto.setQuantity( stock.getQuantity() );
        dto.setDemandForecast( getDemandForestingFor3MonthsTimeUnit( stock.getItemID() ) ); //have to use the method of getting demand forecast
        dto.setLastRestockDate( stock.getLastRestockDate() );

        return dto;
    }
}
