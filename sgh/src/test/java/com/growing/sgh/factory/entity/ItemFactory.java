package com.growing.sgh.factory.entity;

import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemSellStatus;

public class ItemFactory {

    public static Item createItem(){
        return Item.builder()
                .id(1L)
                .itemNm("상품1")
                .category(CategoryFactory.createCategory())
                .itemSellStatus(ItemSellStatus.SELL)
                .description("설명")
                .price(1000)
                .stockNumber(1000)
                .build();
    }

}
