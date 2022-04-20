package com.growing.sgh.domain.item.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum ItemSellStatus {
    SELL, SOLD_OUT;

    @JsonCreator
    public static ItemSellStatus from(String s){
        return ItemSellStatus.valueOf(s.toUpperCase());
    }
}
