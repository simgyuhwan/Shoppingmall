package com.growing.sgh.domain.item.entity;

import com.growing.sgh.domain.item.dto.ItemDto;
import com.growing.sgh.domain.member.entity.BaseEntity;
import com.growing.sgh.exception.order.OutOfStockException;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "item")
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
    public Item(Long id,String itemNm,Integer price ,Integer stockNumber, String description, ItemSellStatus itemSellStatus){
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.description = description;
        this.itemSellStatus = itemSellStatus;
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0) throw new OutOfStockException();
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

    public void updateItem(ItemDto itemDto){
        this.itemNm = itemDto.getItemNm();
        this.description = itemDto.getDescription();
        this.stockNumber = itemDto.getStockNumber();
        this.price = itemDto.getPrice();
        this.itemSellStatus = itemDto.getItemSellStatus();
    }

}
