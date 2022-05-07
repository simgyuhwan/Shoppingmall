package com.growing.sgh.helper;

import com.growing.sgh.domain.cart.entity.Cart;
import com.growing.sgh.domain.cart.entity.CartItem;
import com.growing.sgh.domain.cart.repository.CartItemRepository;
import com.growing.sgh.domain.cart.repository.CartRepository;
import com.growing.sgh.domain.category.entity.Category;
import com.growing.sgh.domain.category.repository.CategoryRepository;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.domain.order.entity.Order;
import com.growing.sgh.domain.order.repository.OrderRepository;
import com.growing.sgh.exception.cart.CartItemNotFoundException;
import com.growing.sgh.exception.category.CategoryNotFoundException;
import com.growing.sgh.exception.item.ItemImgNotFoundException;
import com.growing.sgh.exception.item.ItemNotFoundException;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.exception.order.OrderNotFoundException;

public class ServiceFindHelper {

    public static Member findExistingMember(MemberRepository repository, Long memberId){
        return repository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    public static Item findExistingItem(ItemRepository repository, Long itemId){
        return repository.findById(itemId).orElseThrow(ItemNotFoundException::new);
    }

    public static Order findExistingOrder(OrderRepository repository, Long orderId){
        return repository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public static Cart findExistingCartByMemberId(CartRepository repository, Long memberId){
        return repository.findByMemberId(memberId);
    }

    public static CartItem findExistingCartItem(CartItemRepository repository, Long cartItemId){
        return repository.findById(cartItemId).orElseThrow(CartItemNotFoundException::new);
    }

    public static Category findExistingCategory(CategoryRepository repository, Long categoryId){
        return repository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }

}
