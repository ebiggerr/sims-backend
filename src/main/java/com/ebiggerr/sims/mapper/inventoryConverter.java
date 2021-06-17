package com.ebiggerr.sims.mapper;

import com.ebiggerr.sims.domain.inventory.item;
import com.ebiggerr.sims.domain.inventory.itemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class inventoryConverter {

    public itemDTO entityToDTO(item item){

        itemDTO dto= new itemDTO();
        dto.setId(item.getId());
        dto.setSKU(item.getSKU());
        dto.setImagePath(item.getImagePath());
        dto.setThumbnailImagePath(item.getImagePath()); //append "-thumbnail" to the string
        dto.setItemName(item.getItemName());
        dto.setDimensions(item.getDimensions());
        dto.setVolume(item.getVolume());;
        dto.setItemDescription(item.getItemDescription());
        dto.setUnitPrice( item.getUnitPrice() );

        return dto;

    }

    public List<itemDTO> entitiesToDTO(List<item> itemList){

        return itemList.stream().map(this::entityToDTO).collect(Collectors.toList());

    }
}

