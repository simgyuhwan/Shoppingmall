package com.growing.sgh.domain.member.service;

import com.growing.sgh.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface MemberService {
    public void register(Member member) throws Exception;
    public void modify(Member member) throws Exception;
    public void remove(Long userNo) throws Exception;
}
