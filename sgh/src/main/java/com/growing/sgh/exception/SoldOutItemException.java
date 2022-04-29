package com.growing.sgh.exception;

public class SoldOutItemException extends RuntimeException{
    public SoldOutItemException() {
    }

    public SoldOutItemException(String message) {
        super(message);
    }
}
