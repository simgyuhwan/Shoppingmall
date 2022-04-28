package com.growing.sgh.domain.order.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.config.security.entity.CustomUser;
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
    public Response order(@RequestBody @Validated OrderDto orderDto, @AuthenticationPrincipal User user) {
        orderService.order(orderDto, ((CustomUser) user).getMemberId());
        return Response.success();
    }

    @PostMapping("/{orderId}/cancel")
    public Response cancelOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal User user){
        orderService.cancelOrder(orderId, ((CustomUser) user).getMemberId());
        return Response.success();
    }

    @GetMapping("/{page}")
    public Response orderHist(@PathVariable("page")Optional<Integer> page, @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        return Response.success(orderService.getOrders(((CustomUser) user).getUsername(), pageable));
    }


}