package com.growing.sgh.domain.cart.service;

import com.growing.sgh.domain.cart.entity.CartItem;
import com.growing.sgh.domain.cart.service.CartService;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.order.entity.Order;
import com.growing.sgh.domain.order.entity.OrderItem;
import com.growing.sgh.domain.order.repository.OrderItemRepository;
import com.growing.sgh.domain.order.repository.OrderRepository;
import com.growing.sgh.domain.order.service.OrderService;
import com.growing.sgh.exception.item.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartOrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public Long orders(List<CartItem> cartItems, Member member) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Item item = itemRepository.findById(cartItem.getItem().getId()).orElseThrow(ItemNotFoundException::new);
            orderItemList.add(OrderItem.createOrderItem(item, cartItem.getCount()));
        }

        Order order = orderRepository.save(Order.createOrder(member, orderItemList));
        return order.getId();
    }
}
