package com.growing.sgh.domain.item.dto;

import com.growing.sgh.domain.item.entity.ItemSellStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemSearchDto {
    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    // itemNm : 상품명, createBy : 판매자
    private String searchBy;

    private Integer maxPrice;
    private Integer minPrice;

    private String searchQuery = "";
}
