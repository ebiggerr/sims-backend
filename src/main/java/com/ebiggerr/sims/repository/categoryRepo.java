package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.dashboard.category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface categoryRepo extends JpaRepository<category,Long> {


    @Query(value="select listing.categoryid,category.categoryname, count(listing.categoryid) as value from itemlisting listing join itemcategory category ON listing.categoryid = category.categoryid group by listing.categoryid, category.categoryname order by listing.categoryid asc" ,nativeQuery=true)
    List<category> getCategoricalAnalytics();

    @Query(value="select category.categoryname, count(listing.categoryid) as value from itemlisting listing join itemcategory category ON listing.categoryid = category.categoryid group by listing.categoryid, category.categoryname order by listing.categoryid asc" ,nativeQuery=true)
    List<category> getCategoricalAnalyticsA();

    @Query(value="SELECT categoryid FROM itemcategory WHERE categoryname=?1 ", nativeQuery=true)
    int getCategoryIdByName(String categoryName);

    /*@Query(value="SELECT categoryid, categoryname FROM itemcategory", nativeQuery=true)
    List<category> getAll();*/
}
