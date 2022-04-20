package com.growing.sgh.exception;

public class NicknameAlreadyExistsException extends RuntimeException{
    public NicknameAlreadyExistsException(String message) {
        super(message);
    }
}
