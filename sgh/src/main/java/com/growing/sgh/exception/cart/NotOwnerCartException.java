package com.growing.sgh.exception.cart;

public class NotOwnerCartException extends RuntimeException{
    public NotOwnerCartException() {
    }

    public NotOwnerCartException(String message) {
        super(message);
    }
}
