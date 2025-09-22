package com.shubham.stockmonitoring.commons.exception;

import lombok.Getter;

public class CustomException extends RuntimeException {
    private final String errorCode;
    
    public CustomException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }
    
    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public CustomException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
