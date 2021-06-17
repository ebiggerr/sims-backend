package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.sales.historicalSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface historicalSalesRepo extends JpaRepository<historicalSales, Long> {


    /*@Query(value="SELECT date_trunc('month', timestamp) AS month , SUM( quantity) as salesnumber " +
            "FROM historicalsales2015 WHERE date_trunc('year', timestamp) = " +
            "date_trunc('year', current_date - :duration * interval '1 years' ) GROUP by month order by month", nativeQuery=true)
    List<historicalSales> getByMonth(@Param("duration") int duration);

    @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( timestamp :startDate ,timestamp :endDate,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >= :yearBegin AND h.timestamp <= :yearEnd GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearLocalDateTime(@Param("startDate")LocalDateTime seriesStartDate,
                                             @Param("endDate")LocalDateTime seriesEndDate,
                                             @Param("yearBegin")LocalDateTime yearFirstDate,
                                             @Param("yearEnd")LocalDateTime yearLastDate);*/

    @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( ?1 , ?2 ,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >= ?3 AND h.timestamp <= ?4 GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearLocalDate(LocalDate seriesStartDate,
                                             LocalDate seriesEndDate,
                                             LocalDate yearFirstDate,
                                             LocalDate yearLastDate);

   /* @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( timestamp ?1 ,timestamp ?2,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >=?3 AND h.timestamp <=?4 GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearString(String seriesStartDate,
                                                   String seriesEndDate,
                                                   String yearFirstDate,
                                                   String yearLastDate);

    @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( ?1 , ?2,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >=?3 AND h.timestamp <=?4 GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearTimeStamp(Timestamp seriesStartDate,
                                                   Timestamp seriesEndDate,
                                                   Timestamp yearFirstDate,
                                                   Timestamp yearLastDate);

    @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( timestamp ?1 , timestamp ?2,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >= timestamp ?3 AND h.timestamp <= timestamp ?4 GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearDate(Date seriesStartDate,
                                                 Date seriesEndDate,
                                                 Date yearFirstDate,
                                                 Date yearLastDate);

    @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( timestamp ?1 , timestamp ?2,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >= ?3 AND h.timestamp <= ?4 GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearDate2(Date seriesStartDate,
                                                 Date seriesEndDate,
                                                 Date yearFirstDate,
                                                 Date yearLastDate);*/
}
