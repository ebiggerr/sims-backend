/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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


    @Query(value="SELECT * FROM ( SELECT month"+"\\:\\:" +"date FROM generate_series( ?1 , ?2 ,interval '1 month') month) d LEFT JOIN ( SELECT date_trunc('month', h.timestamp)"+ "\\:\\:" + "date AS month, COALESCE(sum( h.quantity), '0') AS salesnumber FROM historicalsales2015 h WHERE h.timestamp >= ?3 AND h.timestamp <= ?4 GROUP BY 1 ) t USING (month) ORDER BY month", nativeQuery=true)
    List<historicalSales> getByMonthsInAYearLocalDate(LocalDate seriesStartDate,
                                             LocalDate seriesEndDate,
                                             LocalDate yearFirstDate,
                                             LocalDate yearLastDate);

}
