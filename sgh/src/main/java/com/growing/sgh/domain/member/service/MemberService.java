package com.growing.sgh.domain.member.service;

import com.growing.sgh.domain.member.dto.*;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.exception.member.NicknameAlreadyExistsException;
import com.growing.sgh.exception.member.SignInFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void changeMemberInfo(ChangeMemberInfoDto memberInfo, Long memberId){
        duplicateNickname(memberInfo.getNickname());
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.changeInfo(memberInfo);
    }

    public void changePassword(ChangePasswordDto passwordDto,Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.changePassword(passwordDto.getOldPassword(),passwordDto.getNwPassword(), passwordEncoder);
    }

    private void duplicateNickname(String NwNickName){
        if(memberRepository.existsByNickname(NwNickName))
            throw new NicknameAlreadyExistsException(NwNickName);
    }


}
