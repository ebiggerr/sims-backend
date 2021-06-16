package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.stock.stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface stockRepo extends JpaRepository<stock,Long> {

    @Modifying
    @Transactional
    @Query(value="UPDATE inventorystock SET quantitycount=(quantitycount+?1) , lastrestockdate=?2 WHERE itemid=?3" , nativeQuery = true)
    void restock(int restockQuantity, LocalDateTime restockTime, long itemid);

    @Query(value="SELECT itemid FROM inventorystock WHERE skunumber=?1" , nativeQuery = true)
    Optional<Long> getIDFromSKU(String skunumber);

    @Modifying
    @Transactional
    @Query( value="INSERT INTO inventorystock VALUES (?1,?2,?3,?4,'NORMAL','ACTIVE',?5)", nativeQuery=true)
    void addStockForItemIfNone(long itemID,String sku,int quantity,LocalDateTime date,int lowIndicator);

    @Query(value="SELECT i.itemname, s.itemid, s.skunumber, s.quantitycount, s.lastrestockdate FROM inventorystock s INNER JOIN itemlisting i on s.itemid = i.itemid", nativeQuery=true)
    List<stock> getAll();

    @Query(value="SELECT i.itemname, s.itemid, s.skunumber, s.quantitycount, s.lastrestockdate FROM inventorystock s INNER JOIN itemlisting i on s.itemid = i.itemid WHERE s.skunumber=?1 ", nativeQuery=true)
    stock getAnStock(String skunumber);
}
