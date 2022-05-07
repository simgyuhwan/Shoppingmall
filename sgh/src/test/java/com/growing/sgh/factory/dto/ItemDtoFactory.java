package com.growing.sgh.factory.dto;

import com.growing.sgh.domain.item.dto.ItemDto;
import com.growing.sgh.domain.item.entity.ItemSellStatus;

import java.util.ArrayList;

public class ItemDtoFactory {

    public static ItemDto createItemDto(){
        return ItemDto.builder()
                .itemNm("테스트")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(1000)
                .price(1000)
                .description("상품 설명")
                .itemImgDtoList(new ArrayList<>())
                .categoryId(1L)
                .build();
    }
}
