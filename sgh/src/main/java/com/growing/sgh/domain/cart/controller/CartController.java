package com.growing.sgh.domain.cart.controller;

import com.growing.sgh.common.annotation.AuthMember;
import com.growing.sgh.common.response.Response;
import com.growing.sgh.domain.cart.dto.CartItemDto;
import com.growing.sgh.domain.cart.service.CartService;
import com.growing.sgh.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response addCartItem(@Validated CartItemDto cartItemDto, @AuthMember Member member){
        return Response.success(cartService.addCart(cartItemDto, member.getId()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response getCartItems(@AuthMember Member member){
        return Response.success(cartService.getCartList(member.getId()));
    }

    @PutMapping("/cartItem/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, @AuthMember Member member){
        if(count <= 0) return Response.failure(400, "최소 1개 이상 담아주세요.");
        return Response.success(cartService.updateCartItemCount(cartItemId, member.getId(), count));
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteCartItem(@PathVariable("cartItemId") Long cartItemId, @AuthMember Member member){
        cartService.deleteCartItem(cartItemId, member.getId());
        return Response.success();
    }

}
