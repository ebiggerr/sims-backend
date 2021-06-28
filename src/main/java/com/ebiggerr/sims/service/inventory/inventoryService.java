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

package com.ebiggerr.sims.service.inventory;

import com.ebiggerr.sims.domain.dashboard.category;
import com.ebiggerr.sims.domain.inventory.item;
import com.ebiggerr.sims.domain.inventory.itemDTO;
import com.ebiggerr.sims.domain.request.itemWithImageRequest;
import com.ebiggerr.sims.domain.response.inventory;
import com.ebiggerr.sims.mapper.inventoryConverter;
import com.ebiggerr.sims.repository.categoryRepo;
import com.ebiggerr.sims.repository.inventoryRepo;
import com.ebiggerr.sims.service.input.imageUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class inventoryService {

    private final inventoryRepo inventoryRepo;

    private final categoryRepo categoryRepo;

    private final int DEFAULT_PAGE_SIZE=50;

    private final int DEFAULT_FIRST_PAGE=1;

    private final inventoryConverter mapper= new inventoryConverter();

    public inventoryService(inventoryRepo inventoryRepo, categoryRepo categoryRepo){
        this.inventoryRepo=inventoryRepo;
        this.categoryRepo=categoryRepo;
    }

    /**
     *  <h1> Add a new item in the inventory </h1>
     * @param item information of that item
     * @return result of the operation , populated item if successful or null in the case of failure
     * @throws IOException IO exception if there is any error during the operation of writing image file from user request to the resource directory of the project
     */
    public item addNewItemImage(itemWithImageRequest item) throws IOException {

            int id = inventoryRepo.getMaxID();

            Optional<item> findDuplicates = inventoryRepo.getBySKUIs( item.getSKU() );

            if( findDuplicates.isEmpty() ) {

                item repoItem = new item();
                //TODO use Optional
                Optional<Integer> categoryIDOptional = categoryRepo.getCategoryIdByNameOptional( item.getCategoryName() );
                long categoryID;
                if( categoryIDOptional.isPresent() ) {
                    categoryID = categoryIDOptional.get();
                }
                else{
                    return null;
                }

                String imagePath = imageUpload.saveUploadFile(item.getImage(), item.getSKU());

                try {
                    item itemEntity = new item(
                            id + 1, //increment
                            item.getSKU(),
                            imagePath,
                            item.getItemName(),
                            item.getDimensions(),
                            item.getVolume(),
                            item.getItemDescription(),
                            Double.parseDouble(item.getUnitPrice()),
                            categoryID
                    );


                    repoItem = inventoryRepo.save(itemEntity);

                } catch (Exception e) {
                    return null;
                }

                return repoItem;
            }
            else {
                return null;
            }
    }

    /**
     * <h1> Update an existing item in the inventory </h1>
     * @param request information of that item
     * @return result of the operation , populated item if successful or null in the case of failure
     * @throws IOException IO exception if there is any error during the operation of writing image file from user request to the resource directory of the project
     */
    public item updateItemWithImage(itemWithImageRequest request) throws IOException{

        item result = null;

        //if there is an item with matching id in the inventory
        Optional<item> findDuplicates = inventoryRepo.getByIdIs( request.getId() );
        Optional<item> findSKUDuplicate = inventoryRepo.getBySKUIs( request.getSKU() );

        if( findDuplicates.isPresent() && findSKUDuplicate.isEmpty() ){

            String imagePath = imageUpload.saveUploadFile( request.getImage(), request.getSKU() );
            item toBeUpdate = findDuplicates.get();

            toBeUpdate.setSKU( request.getSKU() );
            toBeUpdate.setItemName( request.getItemName() );
            toBeUpdate.setItemDescription( request.getItemDescription() );
            toBeUpdate.setImagePath( imagePath );
            toBeUpdate.setVolume( request.getVolume() );
            toBeUpdate.setDimensions( request.getDimensions() );
            toBeUpdate.setCategoryID( request.getId() );
            toBeUpdate.setUnitPrice( Double.parseDouble(request.getUnitPrice()) );

            try {
                result = inventoryRepo.save(toBeUpdate);
            }catch (Exception e){
                return null;
            }
        }
        else{
            //no record found (matching ID) OR duplicates in SKU
            return null;
        }
        //successful update
        return result;
    }

    /**
     *
     * @param skunumber sku of the item
     * @return status of the operation successful : true ? false
     */
    public boolean removeItem(String skunumber){

        Optional<item> temp = inventoryRepo.getBySKUIs(skunumber);

        if( temp.isPresent() ) {
            try {
                inventoryRepo.deleteBySKUIs(skunumber);
                return true;

            } catch (Exception e) {
                return false;
            }
        }
        else{ return false; }

    }


    /**
     * <h1> Return the listing of items in the inventory, supports paging</h1>
     * @param pageNumber index of which page that want to view
     * @param pageSize size of the page
     * @return [inventory] result wrapped in together with the current page index and total pages available
     */
     @Deprecated
     // replaced by the method @getItemsSortedPageAndSize that with added features such as sorting
    public inventory getItemsPageAndSize(int pageNumber, int pageSize){

        if( pageNumber == 0 || pageNumber < 0 ) pageNumber= DEFAULT_FIRST_PAGE;
        if( pageSize == 0 || pageSize <0 ) pageSize= DEFAULT_PAGE_SIZE;

        PageRequest pageRequest=PageRequest.of(pageNumber-1,pageSize);

        Page<item> listPage= inventoryRepo.findAll(pageRequest);

        List<itemDTO> itemList= mapper.entitiesToDTO( listPage.getContent());

        return new inventory( pageNumber, listPage.getTotalPages(), itemList);

    }

    /**
     * <h1> Return the listing of items in the inventory sorted by item id in ascending form, supports paging </h1>
     * @param pageNumber index of which page that want to view
     * @param pageSize size of the page
     * @return [inventory] result wrapped in together with the current page index and total pages available
     */
    public inventory getItemsSortedPageAndSize(int pageNumber, int pageSize){

        if( pageNumber == 0 || pageNumber < 0 ) pageNumber = DEFAULT_FIRST_PAGE;
        if( pageSize == 0 || pageSize <0 ) pageSize = DEFAULT_PAGE_SIZE;

        PageRequest pageRequest=PageRequest.of(pageNumber-1,pageSize, Sort.by("itemid").ascending());

        Page<item> listPage= inventoryRepo.findAllSorted(pageRequest);

        List<itemDTO> itemList= mapper.entitiesToDTO( listPage.getContent());

        return new inventory( pageNumber, listPage.getTotalPages(), itemList);

    }

    /**
     * <h1> Return the listing of items in the inventory under a specified category (given by the user) sorted by item id in ascending form, supports paging</h1>
     * @param pageNumber index of which page that want to view
     * @param pageSize size of the page
     * @param categoryName name of the category
     * @param categoryID id of the category
     * @return [inventory] result wrapped in together with the current page index and total pages available
     */
    public inventory getItemsByCategoryWithPageAndSize(int pageNumber,int pageSize, String categoryName, String categoryID){

        if( pageNumber == 0 || pageNumber < 0 ) pageNumber = DEFAULT_FIRST_PAGE;
        if( pageSize == 0 || pageSize <0 ) pageSize= DEFAULT_PAGE_SIZE;

        int categoryId = 0;

        categoryId = Integer.parseInt(categoryID);

        if( categoryId == 0 ){

            categoryId = categoryRepo.getCategoryIdByName(categoryName);
        }

        PageRequest pageRequest=PageRequest.of(pageNumber-1,pageSize, Sort.by("itemid").ascending() );

        Page<item> resultPage= inventoryRepo.findAllByCategory( categoryId, pageRequest);

        List<itemDTO> itemListByCategoryAndSorted= mapper.entitiesToDTO( resultPage.getContent());

        return new inventory( pageNumber, resultPage.getTotalPages(), itemListByCategoryAndSorted );

    }

    /**
     * <h1> Get the list of categories in the inventory</h1>
     * @return [List] of all types of categories available in the inventory
     */
    public List<category> getAllCategories(){

        return categoryRepo.getCategoricalAnalytics();
    }


     /**
     * <h1> Return a record of item wrapped in the form of itemDTO with matching SKU number</h1>
     * @param itemSKU SKU number of the item
     * @return [itemDTO] mapper then @entity item to the itemDTO that add more characteristics to the object
     */
    public itemDTO getItem(String itemSKU){

        Optional<item> getItem = Optional.empty();
        getItem = inventoryRepo.getBySKUIs(itemSKU);
        item item = null;
        if( getItem.isPresent() ) item = getItem.get();
        else{ return null; }

        return mapper.entityToDTO( item );
    }

    /**
     * <h1> Return a record of item wrapped in the form of itenDTO with matching item id</h1>
     * @param id id of the item
     * @return [itemDTO] mapped from the @entity item that add more characteristics to the object
     */
    public itemDTO getItemByID(Long id){

        Optional<item> itemTemp = inventoryRepo.getByIdIs(id);

        return itemTemp.map(mapper::entityToDTO).orElse(null);
    }

}

