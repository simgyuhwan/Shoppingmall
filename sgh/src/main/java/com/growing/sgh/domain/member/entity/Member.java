package com.growing.sgh.domain.member.entity;

import com.growing.sgh.domain.member.dto.ChangeMemberInfo;
import com.growing.sgh.domain.member.dto.SignUpRequest;
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

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;

    @Column(name = "address")
    private String address;
//    @Embedded
//    private Address address;
//
//    @Embedded
//    private PhoneNum phoneNum;

    @Column(name = "phoneNum")
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Builder
    public Member(String username, String password, String nickname, String email, String address,String phoneNum, Role role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.role = role;
    }
    public static Member toEntity(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(signUpRequest.getUsername())
                .nickname(signUpRequest.getNickname())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .address(signUpRequest.getAddress())
                .phoneNum(signUpRequest.getPhoneNum())
                .role(Role.MEMBER)
                .build();
    }

    public void changePassword(String nwPassword, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(nwPassword);
    }

    public void changeInfo(ChangeMemberInfo memberInfo){
        this.nickname = memberInfo.getNickname();
        this.address = memberInfo.getNickname();
        this.phoneNum = memberInfo.getPhoneNum();
    }
}
