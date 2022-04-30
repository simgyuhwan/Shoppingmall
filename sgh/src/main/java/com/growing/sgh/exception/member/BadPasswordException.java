package com.growing.sgh.exception.member;

public class BadPasswordException extends RuntimeException{
    public BadPasswordException() {
    }

    public BadPasswordException(String message) {
        super(message);
    }
}
