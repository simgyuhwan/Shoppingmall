package com.growing.sgh.domain.order.service;

import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemImg;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import com.growing.sgh.domain.item.repository.ItemImgRepository;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.domain.order.dto.OrderDto;
import com.growing.sgh.domain.order.dto.OrderHistDto;
import com.growing.sgh.domain.order.dto.OrderItemDto;
import com.growing.sgh.domain.order.entity.Order;
import com.growing.sgh.domain.order.entity.OrderItem;
import com.growing.sgh.domain.order.repository.OrderRepository;
import com.growing.sgh.exception.item.ItemNotFoundException;
import com.growing.sgh.exception.member.MemberDoesNotMatchException;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.exception.order.OrderNotFoundException;
import com.growing.sgh.exception.order.SoldOutItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, Long memberId){
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(ItemNotFoundException::new);
        checkSalesStatus(item);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(OrderItem.createOrderItem(item, orderDto.getCount()));

        Order order = Order.createOrder(member, orderItemList);

        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId, long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        validateOrder(member, order);

        order.cancelOrder();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrders(String username, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(username, pageable);
        Long totalCount = orderRepository.countOrder(username);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();

            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }


    private void checkSalesStatus(Item item) {
        if(item.getItemSellStatus().equals(ItemSellStatus.SOLD_OUT)) throw new SoldOutItemException();
    }

    private void validateOrder(Member member, Order order){
        if(!StringUtils.equals(member.getUsername(), order.getMember().getUsername()))
            throw new MemberDoesNotMatchException();
    }

}
