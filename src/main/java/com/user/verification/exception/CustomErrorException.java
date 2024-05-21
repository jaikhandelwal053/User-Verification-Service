package com.user.verification.exception;



public class CustomErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;

    public CustomErrorException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}