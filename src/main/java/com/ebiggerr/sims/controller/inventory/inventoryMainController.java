package com.ebiggerr.sims.controller.inventory;

import com.ebiggerr.sims.domain.response.API_Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class inventoryMainController {

    @GetMapping(path = "/inventory" )
    public API_Response getAllFromInventory(){




        return new API_Response().Success();
    }

}
