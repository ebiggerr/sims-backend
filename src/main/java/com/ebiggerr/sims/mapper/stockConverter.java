package com.ebiggerr.sims.mapper;

import com.ebiggerr.sims.domain.stock.stock;
import com.ebiggerr.sims.domain.stock.stockDTO;
import com.ebiggerr.sims.service.demandService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class stockConverter {

    private final demandService demandService;

    public stockConverter(demandService demandService) {
        this.demandService = demandService;
    }

    //TODO 08 June 2021
    public stockDTO entityToDTO(stock stock){

        return demandService.populateDTOUsingDemandService(stock);
    }

    //TODO 08 June 2021
    public List<stockDTO> entitiesToDTO(List<stock> stockList){

        return stockList.stream().map(this::entityToDTO).collect(Collectors.toList());

    }

}
