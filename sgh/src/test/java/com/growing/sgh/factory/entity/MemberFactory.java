package com.growing.sgh.factory.entity;

import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class MemberFactory {

    public static Member createMember(){
        return Member.builder()
                .username("test1")
                .password("1111")
                .address("서울시 중랑구")
                .nickname("test1")
                .email("test1@test.com")
                .build();
    }

    public static Member createMemberWithRole(List<Role> roles){
        return Member.builder()
                .username("test1")
                .password("1111")
                .address("서울시 중랑구")
                .nickname("test1")
                .email("test1@test.com")
                .roles(roles)
                .build();
    }

    public static Member passwordEncodeMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .username("test1")
                .password(passwordEncoder.encode("1111"))
                .address("서울시 중랑구")
                .nickname("test1")
                .email("test1@test.com")
                .build();
    }
        public static Member createMember(String username, String password, String email, String nickname){
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .address("서울시 중랑구")
                .build();
    }



}
