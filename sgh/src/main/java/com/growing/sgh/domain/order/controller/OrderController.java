package com.growing.sgh.domain.order.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.config.security.entity.CustomUser;
import com.growing.sgh.domain.order.dto.OrderDto;
import com.growing.sgh.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Response order(@RequestBody @Validated OrderDto orderDto, @AuthenticationPrincipal User user) {
        long memberId = ((CustomUser) user).getMemberId();
        orderService.order(orderDto, memberId);
        return Response.success();
    }

    @PostMapping("/{orderId}/cancel")
    public Response cancelOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal User user){
        long memberId = ((CustomUser) user).getMemberId();
        orderService.cancelOrder(orderId, memberId);
        return Response.success();
    }

    

}