package com.ads.dentalapp.exception;

public class UnpaidBillException extends RuntimeException {
    public UnpaidBillException(String message) {
        super(message);
    }
}