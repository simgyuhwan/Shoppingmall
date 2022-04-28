package com.growing.sgh.config.security;

import com.growing.sgh.config.security.entity.CustomUser;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByUsername(username).orElseThrow(EntityExistsException::new);
        List<SimpleGrantedAuthority> authorities = member.getAuthorities().stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().toString()))
                .collect(Collectors.toList());
        return member == null? null: new CustomUser(member, authorities);
    }

}
