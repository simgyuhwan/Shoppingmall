package com.growing.sgh.common.security;

import com.growing.sgh.common.security.entity.CustomUser;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("===============test====================");
        System.out.println("===============test====================");
        System.out.println("===============test====================");
        System.out.println("username : " + username);
        System.out.println("===============test====================");
        System.out.println("===============test====================");
        System.out.println("===============test====================");
        System.out.println("===============test====================");
        Member member = memberRepository.findOneByUsername(username).orElseThrow(EntityExistsException::new);

        System.out.println("username : " + member.getUsername());
        System.out.println("nickname : " + member.getNickname());
        System.out.println("role : " + member.getRole());
        System.out.println("password : " + member.getPassword());

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));

        return member == null? null: new CustomUser(member, authorities);
    }

}
