package com.growing.sgh.domain.member.service;

import com.growing.sgh.domain.member.dto.ChangeMemberInfoDto;
import com.growing.sgh.domain.member.dto.MemberInfoDto;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.exception.member.NicknameAlreadyExistsException;
import com.growing.sgh.factory.entity.MemberFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks MemberService memberService;
    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 정보 조회 테스트")
    void getMemberInfo(){
        // given
        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        //when
        MemberInfoDto memberInfoDto = memberService.getMemberInfo(1L);

        //then
        assertThat(memberInfoDto.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    @DisplayName("회원 정보 조회 예외 테스트")
    void readExceptionByMemberTest(){
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));

        // when,then
        assertThatThrownBy(()->memberService.getMemberInfo(1L)).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("중복 닉네임 테스트")
    void duplicateNicknameTest(){
        // given
        ChangeMemberInfoDto dto = ChangeMemberInfoDto.builder().nickname("test").address("서울시").build();
        given(memberRepository.existsByNickname(anyString())).willReturn(true);

        // when,then
        assertThatThrownBy(()->memberService.changeMemberInfo(dto, 1L))
                .isInstanceOf(NicknameAlreadyExistsException.class);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMemberTest(){
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(createMember()));

        // when
        memberService.deleteMember(1L);

        // then
        verify(memberRepository).delete(any());
    }

    @Test
    @DisplayName("없는 회원 삭제 예외 테스트")
    void deleteExceptionByMemberNotFoundTest(){
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        // when
        assertThatThrownBy(()-> memberService.deleteMember(1L)).isInstanceOf(MemberNotFoundException.class);
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
}