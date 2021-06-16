package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.demand.demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface demandRepo extends JpaRepository<demand,String> {

    @Query(value="select extract( month FROM date_trunc('month',timestamp) ) as mon, sum(quantity) from saleshistory where timestamp > (current_date - interval '2 months') and itemid= ?1 group by mon order by mon asc" , nativeQuery=true)
    List<demand> getHistoricalSales3Months(long itemid);

    @Query(value="select extract( month FROM date_trunc('month',timestamp) ) as mon, sum(quantity) from saleshistory where timestamp > (current_date - interval '2 months') and itemid= ?1 group by mon order by mon asc" , nativeQuery=true)
    Optional< List<demand> > getHistoricalSales3MonthsB(long itemid);
}
