package com.growing.sgh.domain.item.dto;

import com.growing.sgh.domain.item.entity.ItemSellStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemSearchDto {

    // all, 1d, 1w, 1m, 6m
    private String searchDateType;

    // 상품 상태
    private ItemSellStatus searchSellStatus;

    // itemNm : 상품명
    private String searchBy;

    // 최대 가격
    private Integer maxPrice;

    // 최소 가격
    private Integer minPrice;

    private String searchQuery = "";

    @Builder
    public ItemSearchDto(String searchDateType, ItemSellStatus searchSellStatus, String searchBy, Integer maxPrice, Integer minPrice, String searchQuery) {
        this.searchDateType = searchDateType;
        this.searchSellStatus = searchSellStatus;
        this.searchBy = searchBy;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.searchQuery = searchQuery;
    }
}
