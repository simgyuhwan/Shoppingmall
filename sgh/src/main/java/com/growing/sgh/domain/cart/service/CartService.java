package com.growing.sgh.domain.cart.service;

import com.growing.sgh.domain.cart.dto.CartDetailDto;
import com.growing.sgh.domain.cart.dto.CartItemDto;
import com.growing.sgh.domain.cart.dto.CartOrderDto;
import com.growing.sgh.domain.cart.entity.Cart;
import com.growing.sgh.domain.cart.entity.CartItem;
import com.growing.sgh.domain.cart.repository.CartItemRepository;
import com.growing.sgh.domain.cart.repository.CartRepository;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.helper.MemberServiceHelper;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.exception.cart.CartItemNotFoundException;
import com.growing.sgh.exception.cart.NotOwnerCartException;
import com.growing.sgh.exception.item.ItemImgNotFoundException;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.helper.ServiceFindHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final CartOrderService cartOrderService;


    public Long addCart(CartItemDto cartItemDto, Long memberId) {
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(ItemImgNotFoundException::new);
        Member member = ServiceFindHelper.findExistingMember(memberRepository, memberId);
        Cart cart = ServiceFindHelper.findExistingCartByMemberId(cartRepository,memberId);

        // 첫 장바구니 사용시, 장바구니 생성
        checkMemberCart(cart, member);

        CartItem saveCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(Objects.isNull(saveCartItem)){
            saveCartItem.addCount(cartItemDto.getCount());
            return saveCartItem.getId();
        }else{
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(Long memberId) {
        Cart cart = ServiceFindHelper.findExistingCartByMemberId(cartRepository,memberId);
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        if(Objects.isNull(cart)) return cartDetailDtoList;
        return cartItemRepository.findCartDetailDtoList(cart.getId());
    }

    public Long updateCartItemCount(Long cartItemId, Long memberId, int count) {
        CartItem cartItem = ServiceFindHelper.findExistingCartItem(cartItemRepository, cartItemId);
        validateCart(memberId, cartItem.getCart());
        cartItem.updateCount(count);
        return cartItem.getId();
    }

    public void deleteCartItem(Long cartItemId, Long memberId) {
        CartItem cartItem = ServiceFindHelper.findExistingCartItem(cartItemRepository, cartItemId);
        validateCart(memberId, cartItem.getCart());
        cartItemRepository.deleteById(cartItemId);
    }

    private void validateCart(Long memberId, Cart cart) {
        if(!cart.matchCartMember(memberId)) throw new NotOwnerCartException();
    }

    private void checkMemberCart(Cart cart, Member member) {
        if(Objects.isNull(cart)){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
    }

    public Long orderCartItem(CartOrderDto cartOrderDto, Long memberId) {
        CartItem cartItem = ServiceFindHelper.findExistingCartItem(cartItemRepository, cartOrderDto.getCartItemId());
        Member member = ServiceFindHelper.findExistingMember(memberRepository, memberId);
        validateCart(memberId, cartItem.getCart());

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        List<CartItem> cartItems = new ArrayList<>();
        for (CartOrderDto cartDto : cartOrderDtoList) {
            cartItems.add(ServiceFindHelper.findExistingCartItem(cartItemRepository, cartDto.getCartItemId()));
        }

        Long orderId = cartOrderService.orders(cartItems, member);

        for (CartItem deleteCartItem : cartItems) {
            cartItemRepository.delete(deleteCartItem);
        }

        return orderId;
    }
}
