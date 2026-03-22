package com.breixo.library.infrastructure.adapter.input.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.breixo.library.domain.exception.LibraryException;
import com.breixo.library.infrastructure.adapter.input.web.dto.ApiErrorV1;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<ApiErrorV1> handleException(final LibraryException ex) {
        ApiErrorV1 error = ApiErrorV1.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }
}
