package com.growing.sgh.domain.member.controller;

import com.growing.sgh.advice.ExceptionAdvice;
import com.growing.sgh.domain.member.service.MemberService;
import com.growing.sgh.exception.member.MemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MemberControllerAdviceTest {
    @InjectMocks MemberController memberController;
    @Mock
    MemberService memberService;
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).setControllerAdvice(new ExceptionAdvice()).build();
    }

    @Test
    @DisplayName("회원 조회 예외 테스트")
    void readMemberNotFoundExceptionTest() throws Exception{
        // given
        given(memberService.getMemberInfo(anyLong())).willThrow(MemberNotFoundException.class);

        // when, then
        mockMvc.perform(
                get("/members/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("회원 삭제시 조회 예외 테스트")
    void deleteMemberNotFoundExceptionTest() throws Exception{
        // given
        doThrow(MemberNotFoundException.class).when(memberService).deleteMember(anyLong());

        // when, then
        mockMvc.perform(
                delete("/members/{id}", 1L)
        ).andExpect(status().isNotFound());
    }
}
