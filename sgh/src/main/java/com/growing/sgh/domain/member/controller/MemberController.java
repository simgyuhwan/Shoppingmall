package com.growing.sgh.domain.member.controller;

import com.growing.sgh.common.annotation.AuthMember;
import com.growing.sgh.common.response.Response;
import com.growing.sgh.config.security.entity.CustomUser;
import com.growing.sgh.domain.member.dto.ChangeMemberInfoDto;
import com.growing.sgh.domain.member.dto.ChangePasswordDto;
import com.growing.sgh.domain.member.entity.Member;
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
                                   @AuthMember Member member){
        memberService.changePassword(passwordDto, member.getId() );
        return success();
    }
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public Response changeMemberInfo(@Validated @RequestBody ChangeMemberInfoDto memberInfo,
                                     @AuthMember Member member){
        memberService.changeMemberInfo(memberInfo, member.getId());
        return success();
    }

}
