package com.growing.sgh.factory.entity;

import com.growing.sgh.domain.item.entity.ItemImg;

public class ItemImgFactory {
    public static ItemImg createItemImg(){
        return new ItemImg("imageName", "oriImgName", "imgUrl","Y", ItemFactory.createItem());
    }
}
