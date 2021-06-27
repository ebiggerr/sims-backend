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
