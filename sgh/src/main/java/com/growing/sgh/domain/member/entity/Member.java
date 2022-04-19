package com.growing.sgh.domain.member.entity;

import com.growing.sgh.domain.member.dto.SignUpDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;

//    @Embedded
//    private Address address;
//
//    @Embedded
//    private PhoneNum phoneNum;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = Role.MEMBER;
    }

    public static Member toEntity(SignUpDto signUpDto, PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
    }

}
