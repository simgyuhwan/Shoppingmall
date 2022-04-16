package com.growing.sgh.common.security.entity;

import com.growing.sgh.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;
    private Member member;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }

    public CustomUser(Member member,Collection<? extends GrantedAuthority> authorities ){
        super(member.getEmail(), member.getPassword(),
                authorities);
        this.member = member;
    }

    public long getMemberId(){
        return member.getId();
    }

    public String getMemberEmail(){
        return member.getEmail();
    }

}
