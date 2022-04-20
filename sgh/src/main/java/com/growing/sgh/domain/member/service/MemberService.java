package com.growing.sgh.domain.member.service;

import com.growing.sgh.exception.*;
import com.growing.sgh.common.security.provider.JwtTokenProvider;
import com.growing.sgh.domain.member.dto.*;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.entity.Role;
import com.growing.sgh.domain.member.entity.RoleType;
import com.growing.sgh.domain.member.repository.MemberRepository;
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
        duplicateNickname(memberInfo);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.changeInfo(memberInfo);
    }

    public void changePassword(ChangePasswordDto passwordDto,Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        validatePassword(passwordDto.getOldPassword(), member);
        member.changePassword(passwordDto.getNwPassword(), passwordEncoder);
    }

    private void validatePassword(String password, Member member){
        if(!passwordEncoder.matches(password, member.getPassword()))
            throw new SignInFailureException();
    }

    private void duplicateNickname(ChangeMemberInfoDto memberInfo){
        if(memberRepository.existsByNickname(memberInfo.getNickname()))
            throw new NicknameAlreadyExistsException(memberInfo.getNickname());
    }


}
