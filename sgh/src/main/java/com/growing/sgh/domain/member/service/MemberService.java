package com.growing.sgh.domain.member.service;

import com.growing.sgh.common.exception.*;
import com.growing.sgh.common.security.provider.JwtTokenProvider;
import com.growing.sgh.domain.member.dto.ChangePasswordDto;
import com.growing.sgh.domain.member.dto.SignInRequest;
import com.growing.sgh.domain.member.dto.SignInResponse;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.entity.Role;
import com.growing.sgh.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    public void signUp(SignUpRequest req){
        validateSignUp(req);
        memberRepository.save(Member.toEntity(req, passwordEncoder));
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest req){
        Member member = memberRepository.findOneByUsername(req.getUsername()).orElseThrow(MemberNotFoundException::new);
        validatePassword(req.getPassword(), member);
        String token = tokenProvider.createToken(member.getUsername(), member.getId());
        return new SignInResponse(token);
    }

    public void changePassword(ChangePasswordDto passwordDto,Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        validatePassword(passwordDto.getOldPassword(), member);
        member.changePassword(passwordDto.getNwPassword(), passwordEncoder);
    }

    public void modify(Member member) throws Exception {

    }

    public void remove(Long userNo) throws Exception {

    }

    private void validatePassword(String password, Member member){
        if(!passwordEncoder.matches(password, member.getPassword()))
            throw new SignInFailureException();
    }

    private void validateSignUp(SignUpRequest signUpRequest){
        if(memberRepository.existsByUsername(signUpRequest.getUsername()))
            throw new UsernameAlreadyExistsException(signUpRequest.getUsername());
        if(memberRepository.existsByNickname(signUpRequest.getNickname()))
            throw new NicknameAlreadyExistsException(signUpRequest.getNickname());
        if(memberRepository.existsByEmail(signUpRequest.getEmail()))
            throw new EmailAlreadyExistsException(signUpRequest.getEmail());
    }
}
