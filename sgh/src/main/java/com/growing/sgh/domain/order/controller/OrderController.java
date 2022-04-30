package com.growing.sgh.domain.order.controller;

import com.growing.sgh.common.annotation.AuthMember;
import com.growing.sgh.common.response.Response;
import com.growing.sgh.config.security.entity.CustomUser;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.order.dto.OrderDto;
import com.growing.sgh.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Response order(@RequestBody @Validated OrderDto orderDto, @AuthMember Member member) {
        return Response.success(orderService.order(orderDto, member.getId()));
    }

    @PostMapping("/{orderId}/cancel")
    public Response cancelOrder(@PathVariable("orderId") Long orderId, @AuthMember Member member){
        orderService.cancelOrder(orderId, member.getId());
        return Response.success();
    }

    @GetMapping("/{page}")
    public Response orderHist(@PathVariable("page")Optional<Integer> page, @AuthMember Member member){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        return Response.success(orderService.getOrders((member).getUsername(), pageable));
    }


}