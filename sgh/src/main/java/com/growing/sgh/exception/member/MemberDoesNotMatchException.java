package com.growing.sgh.exception.member;

public class MemberDoesNotMatchException extends RuntimeException{
    public MemberDoesNotMatchException() {
    }

    public MemberDoesNotMatchException(String message) {
        super(message);
    }
}
