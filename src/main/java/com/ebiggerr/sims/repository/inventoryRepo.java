package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.inventory.item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface inventoryRepo extends JpaRepository<item,String> {


    @Query()
    List<item> getAll();

    @Query()
    item getItemByID(String itemID);

    @Query()
    void updateItem(item item);

    @Query()
    void deleteItem(String itemID);

    @Query()
    void addNewItem(item item);

}
