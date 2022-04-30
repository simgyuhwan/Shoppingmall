package com.growing.sgh.domain.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CartDetailDto {

    private Long cartItemId;
    private String itemNm;
    private int price;
    private int count;
    private String imgUrl;

    @Builder
    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
