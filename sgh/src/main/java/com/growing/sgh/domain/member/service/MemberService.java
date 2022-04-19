package com.growing.sgh.domain.member.service;

import com.growing.sgh.common.exception.NicknameAlreadyExistsException;
import com.growing.sgh.common.exception.UsernameAlreadyExistsException;
import com.growing.sgh.domain.member.dto.SignUpDto;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpDto signUpDto) throws Exception {
        validateSignUp(signUpDto);
        memberRepository.save(Member.toEntity(signUpDto, passwordEncoder));
    }

    public void modify(Member member) throws Exception {

    }

    public void remove(Long userNo) throws Exception {

    }

    private void validateSignUp(SignUpDto signUpDto){
        if(memberRepository.existsByUsername(signUpDto.getUsername()))
            throw new UsernameAlreadyExistsException(signUpDto.getUsername());
        if(memberRepository.existsByNickname(signUpDto.getNickname()))
            throw new NicknameAlreadyExistsException(signUpDto.getNickname());
    }
}
