package com.growing.sgh.domain.order.entity;

import com.growing.sgh.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer orderPrice;

    private int count;

    @Builder
    public OrderItem(Order order, Item item, Integer orderPrice, int count) {
        this.order = order;
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void updateOrder(Order order) {
        if(Objects.nonNull(order)) this.order = order;
    }

    public Integer getTotalPrice(){
        return orderPrice * count;
    }

    public void cancel(){
        this.getItem().addStock(count);
    }

    public static OrderItem createOrderItem(Item item, int count){
        item.removeStock(count);
        return OrderItem.builder()
                .item(item)
                .count(count)
                .orderPrice(item.getPrice())
                .build();
    }
}
