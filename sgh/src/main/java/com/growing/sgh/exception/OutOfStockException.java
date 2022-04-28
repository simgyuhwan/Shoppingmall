package com.growing.sgh.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
