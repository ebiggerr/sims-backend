
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

import com.ebiggerr.sims.domain.inventory.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Item,Long> {

    Page<Item> findAll(Pageable page);

    @Query( value="SELECT itemid,skunumber,imagepathfull,itemname,itemdimension,itemvolume,itemdescription,itemunitprice FROM itemlisting",
            countQuery="SELECT count(itemid) from itemlisting",nativeQuery=true)
    Page<Item> findAllSorted(Pageable page);

    @Query( value="SELECT itemid,skunumber,imagepathfull,itemname,itemdimension,itemvolume,itemdescription,itemunitprice FROM itemlisting WHERE categoryid=:id",
            countQuery="SELECT count(itemid) from itemlisting WHERE categoryid=:id",nativeQuery=true)
    Page<Item>findAllByCategory(@Param("id") int categoryId, Pageable page);

    Optional<Item> getBySKUIs(String skunumber);

    @Modifying
    @Transactional
    @Query(value="DELETE from itemlisting WHERE skunumber=?1", nativeQuery=true)
    void deleteBySKUIs(String skunumber);

    Optional<Item> getByIdIs(Long id);

    @Query(value="SELECT MAX(itemid) FROM itemlisting", nativeQuery=true)
    int getMaxID();
}

