package com.sparta.popupstore.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ApiError> apiException(CustomApiException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ApiError(e.getErrorCode().getMessage(), e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        // 테스트해봤는데 없어도 잘 나오네요 혹시 모르니까 다른분들도 테스트 해보시는게 좋을 거 같아욤
        //e.getBindingResult().getGlobalErrors().forEach(error -> errorMap.put(error.getObjectName(), error.getDefaultMessage()));
        e.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMap);
    }
}
