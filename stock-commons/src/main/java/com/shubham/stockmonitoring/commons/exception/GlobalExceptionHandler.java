package com.shubham.stockmonitoring.commons.exception;

import com.shubham.stockmonitoring.commons.dto.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(CustomException.class)
        public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex, HttpServletRequest request) {
            log.warn("Custom exception: {} - {}", ex.getErrorType(), ex.getCustomMessage());

            BaseResponse<Object> response = BaseResponse.error(
                    ex.getHttpStatus().name(),
                    ex.getCustomMessage() != null ? ex.getCustomMessage() : "An error occurred"
            );

            return new ResponseEntity<>(response, ex.getHttpStatus());
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<BaseResponse<Object>> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
            log.warn("Authentication failed: {}", ex.getMessage());

            BaseResponse<Object> response = BaseResponse.error(
                    "AUTHENTICATION_FAILED",
                    "Invalid username or password"
            );

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<BaseResponse<Object>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
            log.warn("Authentication exception: {}", ex.getMessage());

            BaseResponse<Object> response = BaseResponse.error(
                    "AUTHENTICATION_FAILED",
                    "Authentication failed"
            );

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex, HttpServletRequest request) {
            // Only unexpected exceptions are logged as ERROR
            log.error("Unexpected error occurred", ex);

            BaseResponse<Object> response = BaseResponse.error(
                    "INTERNAL_ERROR",
                    "An unexpected error occurred"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
