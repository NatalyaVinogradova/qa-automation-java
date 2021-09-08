package com.tinkoff.edu.app.models;

public class LoanBusinessException extends RuntimeException {
    public LoanBusinessException(String message) {
        super(message);
    }

    public LoanBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
