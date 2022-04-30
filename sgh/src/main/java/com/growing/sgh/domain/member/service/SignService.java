package com.growing.sgh.domain.member.service;

import com.growing.sgh.config.security.provider.JwtTokenProvider;
import com.growing.sgh.domain.member.dto.SignInRequest;
import com.growing.sgh.domain.member.dto.SignInResponse;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.entity.Role;
import com.growing.sgh.domain.member.entity.RoleType;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.exception.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public void signUp(SignUpRequest req){
        validateSignUp(req);
        Member member = SignUpRequest.toEntity(req, passwordEncoder);
        member.addAuth(new Role(member.getId(), RoleType.MEMBER));
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest req){
        Member member = memberRepository.findOneByUsername(req.getUsername()).orElseThrow(MemberNotFoundException::new);
        validatePassword(req, member);
        String token = tokenProvider.createToken(member.getUsername(), member.getId());
        return new SignInResponse(token);
    }

    private void validatePassword(SignInRequest req, Member member) {
        if(!member.matchPassword(req.getPassword(), passwordEncoder)) throw new BadPasswordException();
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
