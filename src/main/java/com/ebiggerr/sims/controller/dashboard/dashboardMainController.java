package com.ebiggerr.sims.controller.dashboard;

import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.service.dashboard.dashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dashboardMainController {

    private final dashboardService dashboardService;

    public dashboardMainController(dashboardService dashboardService){
        this.dashboardService=dashboardService;
    }

    /**
     * <h1> Dashboard </h1>
     *
     * Analytics information for dashboard module
     *
     * @return Information wrapped in dashboard object
     */
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping(path = "/analytics/all")
    public API_Response getAllAnalytics(){

        return new API_Response().Success( dashboardService.getAnalytics() );
    }


    /*@GetMapping(path = "/analytics/inventory_composition")
    public API_Response getInventoryComposition(){

        return new API_Response().Success();
    }

    @GetMapping(path = "/analytics/inventory_statistics")
    public API_Response getInventoryStatistics(){

        return new API_Response().Success();
    }*/

}
