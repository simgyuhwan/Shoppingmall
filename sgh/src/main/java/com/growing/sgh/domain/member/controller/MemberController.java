package com.growing.sgh.domain.member.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.config.security.entity.CustomUser;
import com.growing.sgh.domain.member.dto.ChangeMemberInfoDto;
import com.growing.sgh.domain.member.dto.ChangePasswordDto;
import com.growing.sgh.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.growing.sgh.common.response.Response.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public Response changePassword(@Validated @RequestBody ChangePasswordDto passwordDto,
                                   @AuthenticationPrincipal User user){
        memberService.changePassword(passwordDto, ((CustomUser) user).getMemberId() );
        return success();
    }
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public Response changeMemberInfo(@Validated @RequestBody ChangeMemberInfoDto memberInfo,
                               @AuthenticationPrincipal User user){
        memberService.changeMemberInfo(memberInfo, ((CustomUser) user).getMemberId());
        return success();
    }

}
