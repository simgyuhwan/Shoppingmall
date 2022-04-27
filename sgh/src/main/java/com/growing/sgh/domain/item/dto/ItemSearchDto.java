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

    private String searchBy;

    private Long maxPrice;
    private Long minPrice;

    private String searchQuery = "";
}
