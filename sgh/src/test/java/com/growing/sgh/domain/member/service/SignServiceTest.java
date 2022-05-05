package com.growing.sgh.domain.member.service;

import com.growing.sgh.config.security.provider.JwtTokenProvider;
import com.growing.sgh.domain.member.dto.SignInRequest;
import com.growing.sgh.domain.member.dto.SignInResponse;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.factory.entity.MemberFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignServiceTest {
    @InjectMocks SignService signService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider tokenProvider;


    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest(){
        // given
        SignUpRequest signUpReq = createSignUpRequest();
        given(passwordEncoder.encode(any())).willReturn(any());

        // when
        signService.signUp(signUpReq);

        // then
        verify(passwordEncoder).encode(signUpReq.getPassword());
        verify(memberRepository).save(any());
    }

    @Test
    @DisplayName("로그인 테스트")
    void signInTest(){

    }

    private SignInRequest createSignMember(){
        return new SignInRequest("test", "1111");
    }

    private Member createMember(){
        return MemberFactory.createMember();
    }

    private Member createMember(String username, String password, String email, String nickname){
        return MemberFactory.createMember(username, password, email, nickname);
    }
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private Member securityMember(){
        return MemberFactory.passwordEncodeMember(passwordEncoder());
    }

    private SignUpRequest createSignUpRequest(){
        return SignUpRequest.builder()
                .username("test1")
                .password("password")
                .nickname("test1")
                .email("test@test.com")
                .address("서울시 마포구")
                .build();
    }
}