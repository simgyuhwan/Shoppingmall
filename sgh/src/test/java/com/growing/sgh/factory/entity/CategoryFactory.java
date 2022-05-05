package com.growing.sgh.factory.entity;

import com.growing.sgh.domain.category.entity.Category;

public class CategoryFactory {

    public static Category createCategory(){
        return Category.builder()
                .name("카테고리1")
                .depth(1L)
                .build();
    }
}
