package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.stock.restockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface restockHistoryRepo extends JpaRepository<restockHistory,Long> {

    @Modifying
    @Transactional
    @Query(value=" INSERT INTO restockhistory (itemid,quantity,restocktime) VALUES (?1,?2,?3)", nativeQuery=true)
    void addNewHistory(Long itemid, int quantity, LocalDateTime restockTime);

}
