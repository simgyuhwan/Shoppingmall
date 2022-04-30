package com.growing.sgh.domain.cart.entity;

import com.growing.sgh.domain.member.entity.BaseEntity;
import com.growing.sgh.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cart extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Cart(Member member) {
        this.member = member;
    }

    public static Cart createCart(Member member){
        return Cart.builder()
                .member(member)
                .build();
    }

}
