package com.korit.backend_mini.security.handler;

import com.korit.backend_mini.dto.Response.ApiRespDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.ok(ApiRespDto.builder()
                .status("failed")
                .message("문제 발생 : " + e.getMessage())
                .build());
    }
}
