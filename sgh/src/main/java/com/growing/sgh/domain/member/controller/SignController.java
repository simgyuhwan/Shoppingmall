package com.growing.sgh.domain.member.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.domain.member.dto.SignInRequest;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.domain.member.service.MemberService;
import com.growing.sgh.domain.member.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.growing.sgh.common.response.Response.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignController {
    private final SignService signService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Response signUp(@Validated @RequestBody SignUpRequest req) {
        signService.signUp(req);
        return success();
    }

    @PostMapping("signin")
    @ResponseStatus(HttpStatus.OK)
    public Response signin(@Validated @RequestBody SignInRequest req){
        return success(signService.signIn(req));
    }

}
