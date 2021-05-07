package com.ebiggerr.sims.controller.dashboard;

import com.ebiggerr.sims.domain.response.API_Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dashboardMainController {

    @GetMapping(path = "/analytics/inventory_composition")
    public API_Response getInventoryComposition(){

        return new API_Response().Success();
    }

    @GetMapping(path = "/analytics/inventory_statistics")
    public API_Response getInventoryStatistics(){

        return new API_Response().Success();
    }

}
