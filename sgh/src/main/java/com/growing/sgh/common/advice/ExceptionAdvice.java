package com.growing.sgh.common.advice;

import com.growing.sgh.common.exception.NicknameAlreadyExistsException;
import com.growing.sgh.common.exception.UsernameAlreadyExistsException;
import com.growing.sgh.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;

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


}
