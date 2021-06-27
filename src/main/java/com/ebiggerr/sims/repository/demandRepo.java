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
