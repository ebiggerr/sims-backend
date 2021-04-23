package com.ebiggerr.sims.service.inventory;

import com.ebiggerr.sims.domain.inventory.item;
import com.ebiggerr.sims.domain.request.itemRequest;
import com.ebiggerr.sims.repository.inventoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class inventoryService {

    private final inventoryRepo inventoryRepo;

    public inventoryService(inventoryRepo inventoryRepo){
        this.inventoryRepo=inventoryRepo;
    }

    public void addNewItem(itemRequest item){


    }

    public void updateItem(itemRequest item){

    }

    public void removeItem(String itemID){

    }

    public List<item> getAllItems(){


        return null;
    }

    public item getItem(String itemID){


        return null;
    }




}
