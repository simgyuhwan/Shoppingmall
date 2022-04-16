package com.growing.sgh.domain.member.service;

import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void register(Member member) throws Exception {

    }

    public void modify(Member member) throws Exception {

    }

    public void remove(Long userNo) throws Exception {

    }
}
