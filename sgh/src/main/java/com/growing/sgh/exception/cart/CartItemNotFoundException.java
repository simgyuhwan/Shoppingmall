package com.growing.sgh.exception.cart;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException() {
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
