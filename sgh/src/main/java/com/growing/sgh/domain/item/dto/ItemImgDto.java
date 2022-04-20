package com.growing.sgh.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemImgDto {

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;
}
