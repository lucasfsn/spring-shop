package com.example.demo.exception;

public class NotAvailableException extends RuntimeException {
    public NotAvailableException(String message) {
        super(message);
    }
}
