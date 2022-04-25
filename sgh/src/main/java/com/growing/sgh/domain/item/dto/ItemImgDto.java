package com.growing.sgh.domain.item.dto;

import com.growing.sgh.domain.item.entity.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemImgDto {

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    @Builder
    public ItemImgDto(String imgName, String oriImgName, String imgUrl, String repImgYn) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
    }

    public static ItemImgDto of(ItemImg itemImg){
        return ItemImgDto.builder()
                .imgName(itemImg.getImgName())
                .oriImgName(itemImg.getOriImgName())
                .imgUrl(itemImg.getImgUrl())
                .repImgYn(itemImg.getRepImgYn()).build();
    }
}
