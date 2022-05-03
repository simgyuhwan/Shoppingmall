package com.growing.sgh.domain.member.service;

import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignServiceTest {
    @InjectMocks SignService signService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest(){
        // given
        SignUpRequest signUpReq = createSignUpRequest();
        BDDMockito.given(passwordEncoder.encode(any())).willReturn(any());

        // when
        signService.signUp(signUpReq);

        // then
        verify(passwordEncoder).encode(signUpReq.getPassword());
        verify(memberRepository).save(any());
    }

    public SignUpRequest createSignUpRequest(){
        return SignUpRequest.builder()
                .username("test1")
                .password("password")
                .nickname("test1")
                .email("test@test.com")
                .address("서울시 마포구")
                .build();
    }
}