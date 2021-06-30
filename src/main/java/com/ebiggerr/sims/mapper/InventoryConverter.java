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

package com.ebiggerr.sims.mapper;

import com.ebiggerr.sims.domain.inventory.Item;
import com.ebiggerr.sims.domain.inventory.ItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryConverter {

    public ItemDTO entityToDTO(Item item){

        ItemDTO dto= new ItemDTO();
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

    public List<ItemDTO> entitiesToDTO(List<Item> itemList){

        return itemList.stream().map(this::entityToDTO).collect(Collectors.toList());

    }
}
