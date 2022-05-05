package com.growing.sgh.domain.member.controller;

import com.growing.sgh.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks MemberController memberController;
    @Mock
    MemberService memberService;
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void readMemberTest() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(get("/members/{id}", id))
                .andExpect(status().isOk());
        verify(memberService).getMemberInfo(id);
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMemberTest()throws Exception{
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                delete("/members/{id}", id)
        ).andExpect(status().isOk());
        verify(memberService).deleteMember(id);
    }


}