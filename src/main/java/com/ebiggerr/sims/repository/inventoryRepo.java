
package com.ebiggerr.sims.repository;

import com.ebiggerr.sims.domain.inventory.item;
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
public interface inventoryRepo extends JpaRepository<item,Long> {


    /*@Query()
    List<item> getAll();

    @Query()
    item getItemByID(String itemSKU);

    @Query()
    void updateItem(item item);

    @Query()
    void deleteItem(String itemID);

    @Query()
    void addNewItem(item item);*/

    Page<item> findAll(Pageable page);

    @Query( value="SELECT itemid,skunumber,imagepathfull,itemname,itemdimension,itemvolume,itemdescription,itemunitprice FROM itemlisting",
            countQuery="SELECT count(itemid) from itemlisting",nativeQuery=true)
    Page<item> findAllSorted(Pageable page);

    @Query( value="SELECT itemid,skunumber,imagepathfull,itemname,itemdimension,itemvolume,itemdescription,itemunitprice FROM itemlisting WHERE categoryid=:id",
            countQuery="SELECT count(itemid) from itemlisting WHERE categoryid=:id",nativeQuery=true)
    Page<item>findAllByCategory(@Param("id") int categoryId, Pageable page);

    /*@Query(value="SELECT ", nativeQuery= true)
    List<item> findAllWithoutPaging();*/

    Optional<item> getBySKUIs(String skunumber);

    @Modifying
    @Transactional
    @Query(value="DELETE from itemlisting WHERE skunumber=?1", nativeQuery=true)
    void deleteBySKUIs(String skunumber);

    Optional<item> getByIdIs(Long id);

    @Query(value="SELECT MAX(itemid) FROM itemlisting", nativeQuery=true)
    int getMaxID();
}

