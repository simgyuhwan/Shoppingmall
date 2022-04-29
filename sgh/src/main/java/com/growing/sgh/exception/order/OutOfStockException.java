package com.growing.sgh.exception.order;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
