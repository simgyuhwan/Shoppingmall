package com.growing.sgh.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Embedded
    private Address address;

    @Embedded
    private PhoneNum phoneNum;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String password, String nickname, Address address, PhoneNum phoneNum) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.phoneNum = phoneNum;
        this.role = Role.MEMBER;
    }
}
