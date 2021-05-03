package com.ebiggerr.sims.controller.inventory;

import com.ebiggerr.sims.domain.inventory.itemDetails;
import com.ebiggerr.sims.domain.response.API_Response;
import com.ebiggerr.sims.domain.stock.stockCount;
import org.springframework.web.bind.annotation.*;

@RestController
public class inventoryMainController {

    @GetMapping(path = "/inventory/list" )
    public API_Response getAllFromInventory(){




        return new API_Response().Success();
    }

    @GetMapping(path="/inventory/item/{SKU}")
    public API_Response getAnItemUsingSKUNumber(@PathVariable String SKU){



        return new API_Response().Success();
    }

    @PostMapping(path = "/inventory/removeItem/{SKU}")
    public API_Response deleteAnItem(@PathVariable String SKU){

        return new API_Response().Success();
    }

    @PostMapping(path ="/inventory/updateItem")
    public API_Response updateAnItem(@RequestBody itemDetails item){



        return new API_Response().Success();
    }

    @PostMapping(path ="/inventory/addNewItem")
    public API_Response addNewItem(@RequestBody itemDetails item){



        return new API_Response().Success();
    }

    @PostMapping(path = "/inventory/restock")
    public API_Response restockAnItem(@RequestBody stockCount restockRequest){


        return new API_Response().Success();
    }
}
