package com.shubham.stockmonitoring.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String errorCode;
    
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "Success", data, LocalDateTime.now(), null);
    }
    
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(true, message, data, LocalDateTime.now(), null);
    }
    
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(false, message, null, LocalDateTime.now(), "ERROR");
    }
    
    public static <T> BaseResponse<T> error(String errorCode, String message) {
        return new BaseResponse<>(false, message, null, LocalDateTime.now(), errorCode);
    }
}
