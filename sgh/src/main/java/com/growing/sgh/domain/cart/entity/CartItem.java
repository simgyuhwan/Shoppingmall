package com.growing.sgh.domain.cart.entity;

import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.member.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @Table(name= "cart_Item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    @Builder
    public CartItem(Cart cart, Item item, int count) {
        this.cart = cart;
        this.item = item;
        this.count = count;
    }

    public static CartItem createCartItem(Cart cart, Item item, int count){
        return CartItem.builder()
                .cart(cart)
                .item(item)
                .count(count)
                .build();
    }

    public void addCount(int count){
        this.count += count;
    }

}
