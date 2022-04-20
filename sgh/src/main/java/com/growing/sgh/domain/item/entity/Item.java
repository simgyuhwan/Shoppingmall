package com.growing.sgh.domain.item.entity;

import com.growing.sgh.domain.item.dto.ItemRegisterDto;
import com.growing.sgh.domain.member.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String itemNm;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockNumber;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(String itemNm,Integer price ,Integer stockNumber, String description, ItemSellStatus itemSellStatus){
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.description = description;
        this.itemSellStatus = itemSellStatus;
    }



}
