package com.growing.sgh.exception.order;

public class SoldOutItemException extends RuntimeException{
    public SoldOutItemException() {
    }

    public SoldOutItemException(String message) {
        super(message);
    }
}
