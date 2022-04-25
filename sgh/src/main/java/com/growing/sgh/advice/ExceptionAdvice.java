package com.growing.sgh.advice;

import com.growing.sgh.exception.MemberNotFoundException;
import com.growing.sgh.exception.NicknameAlreadyExistsException;
import com.growing.sgh.exception.RegisterImgNotExistsException;
import com.growing.sgh.exception.UsernameAlreadyExistsException;
import com.growing.sgh.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response exception(Exception e){
        log.info("error : {} ", e.getMessage());
        return Response.failure(500, "오류가 발생했습니다." );
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response UsernameAlreadyExistsException(UsernameAlreadyExistsException e){
        return Response.failure(409, e.getMessage() + "은 중복된 아이디 입니다.");
    }

    @ExceptionHandler(NicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response NicknameAlreadyExistsException(NicknameAlreadyExistsException e){
        return Response.failure(409, e.getMessage() + "은 중복된 닉네임 입니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response MemberNotFoundException(MemberNotFoundException e){
        return Response.failure(404, "가입된 회원이 아닙니다.");
    }

    @ExceptionHandler(RegisterImgNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response RegisterImgNotExistsException(RegisterImgNotExistsException e){
        return Response.failure(400, "이미지 파일은 필수 값입니다.");
    }
}
