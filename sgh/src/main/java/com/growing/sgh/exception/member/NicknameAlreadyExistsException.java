package com.growing.sgh.exception.member;

public class NicknameAlreadyExistsException extends RuntimeException{
    public NicknameAlreadyExistsException(String message) {
        super(message);
    }
}
