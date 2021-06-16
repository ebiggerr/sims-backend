package com.ebiggerr.sims.service.dashboard;

import com.ebiggerr.sims.domain.dashboard.dashboard;
import com.ebiggerr.sims.repository.categoryRepo;
import com.ebiggerr.sims.repository.totalassetsRepo;
import org.springframework.stereotype.Service;

@Service
public class dashboardService {

    private final categoryRepo categoryRepo;
    private final totalassetsRepo totalassetsRepo;

    public dashboardService(categoryRepo categoryRepo,totalassetsRepo totalassetsRepo){
        this.categoryRepo=categoryRepo;
        this.totalassetsRepo=totalassetsRepo;
    }

    /**
     *
     * <h1> Result composition of items by categorical and sum() count , total asset of the inventory</h1>
     *
     * @return result wrapped in @dashboard class
     */
    public dashboard getAnalytics(){

        return new dashboard( categoryRepo.getCategoricalAnalytics(), totalassetsRepo.gettotalassets() );

    }
}
