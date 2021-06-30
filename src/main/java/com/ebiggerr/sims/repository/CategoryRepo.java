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

import com.ebiggerr.sims.domain.dashboard.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {


    @Query(value="select listing.categoryid,category.categoryname, count(listing.categoryid) as value from itemlisting listing join itemcategory category ON listing.categoryid = category.categoryid group by listing.categoryid, category.categoryname order by listing.categoryid asc" ,nativeQuery=true)
    List<Category> getCategoricalAnalytics();

    @Query(value="select category.categoryname, count(listing.categoryid) as value from itemlisting listing join itemcategory category ON listing.categoryid = category.categoryid group by listing.categoryid, category.categoryname order by listing.categoryid asc" ,nativeQuery=true)
    List<Category> getCategoricalAnalyticsA();

    @Query(value="SELECT categoryid FROM itemcategory WHERE categoryname=?1 ", nativeQuery=true)
    int getCategoryIdByName(String categoryName);

    @Query(value="SELECT categoryid FROM itemcategory WHERE categoryname=?1 ", nativeQuery=true)
    Optional<Integer> getCategoryIdByNameOptional(String categoryName);

    /*@Query(value="SELECT categoryid, categoryname FROM itemcategory", nativeQuery=true)
    List<category> getAll();*/
}
