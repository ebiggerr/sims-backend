package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.dashboard.totalassets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface totalassetsRepo extends JpaRepository<totalassets,String> {


    @Query(value="SELECT SUM( stock.quantitycount * listing.itemunitprice ) AS totalassets FROM itemlisting listing join inventorystock stock on listing.itemid = stock.itemid",nativeQuery=true)
    String gettotalassets();
}
