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

    private final inventoryConverter mapper= new inventoryConverter();

    //private DecimalFormat formatter = new DecimalFormat("#0.00");

    public inventoryService(inventoryRepo inventoryRepo, categoryRepo categoryRepo){
        this.inventoryRepo=inventoryRepo;
        this.categoryRepo=categoryRepo;
    }


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

   /* public boolean updateItem(itemRequest item){

        try{
            Optional<item> itemTemp = inventoryRepo.getByIdIs( item.getId() );

            item itemEntity = null;

            if( itemTemp.isPresent() ){
                itemEntity = itemTemp.get();
            }
            else{ return false; }

            itemEntity.setSKU( item.getSKU() );
            itemEntity.setItemName( item.getItemName() );
            itemEntity.setDimensions( item.getDimensions() );
            itemEntity.setVolume( item.getVolume() );
            itemEntity.setImagePath( item.getImagePath() );
            itemEntity.setUnitPrice( Double.parseDouble( item.getUnitPrice() ) );
            itemEntity.setItemDescription( item.getItemDescription() );

            inventoryRepo.save(itemEntity);

        }catch (Exception e){
            return false;
        }

        return true;

    }*/

    public boolean updateItemWithImage(itemWithImageRequest request) throws IOException{



        return true;
    }

    /**
     *
     * @param skunumber
     * @return
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

        if( pageNumber == 0 || pageNumber < 0 ) pageNumber=1;
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

        if( pageNumber == 0 || pageNumber < 0 ) pageNumber=1;
        if( pageSize == 0 || pageSize <0 ) pageSize= DEFAULT_PAGE_SIZE;

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

        if( pageNumber == 0 || pageNumber < 0 ) pageNumber=1;
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

