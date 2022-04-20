package com.growing.sgh.domain.item.dto;

import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRegisterDto {

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotBlank(message = "상품 가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    @NotBlank(message = "상품 상세 정보는 필수 입력 값입니다.")
    private String description;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    public static Item toEntity(ItemRegisterDto registerDto){
        return Item.builder()
                .itemNm(registerDto.getItemNm())
                .price(registerDto.getPrice())
                .stockNumber(registerDto.getStockNumber())
                .description(registerDto.getDescription())
                .itemSellStatus(registerDto.getItemSellStatus())
                .build();
    }
}
