package com.growing.sgh.domain.member.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.common.security.entity.CustomUser;
import com.growing.sgh.common.security.provider.JwtTokenProvider;
import com.growing.sgh.domain.member.dto.ChangeMemberInfo;
import com.growing.sgh.domain.member.dto.ChangePasswordDto;
import com.growing.sgh.domain.member.dto.SignInRequest;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.growing.sgh.common.response.Response.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/changepassword")
    @ResponseStatus(HttpStatus.OK)
    public Response changePassword(@Validated @RequestBody ChangePasswordDto passwordDto,
                                   @AuthenticationPrincipal User user){
        memberService.changePassword(passwordDto, ((CustomUser) user).getMemberId() );
        return success();
    }
    @PostMapping("/changeMemberInfo")
    @ResponseStatus(HttpStatus.OK)
    public Response changeInfo(@Validated @RequestBody ChangeMemberInfo memberInfo,
                               @AuthenticationPrincipal User user){
        memberService.changeMemberInfo(memberInfo, ((CustomUser) user).getMemberId());
        return success();
    }

}
