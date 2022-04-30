package com.growing.sgh.domain.cart.service;

import com.growing.sgh.domain.cart.repository.CartItemRepository;
import com.growing.sgh.domain.cart.repository.CartRepository;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;





}
