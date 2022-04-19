package com.growing.sgh.domain.member.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.domain.member.dto.SignUpDto;
import com.growing.sgh.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public Response signUp(@Validated @RequestBody SignUpDto signUpDto) throws Exception {
        memberService.signUp(signUpDto);
        return Response.success();
    }


}
