package com.breixo.library.infrastructure.adapter.input.web.handler;

import com.breixo.library.domain.exception.IsbnException;
import com.breixo.library.domain.exception.LibraryException;
import com.breixo.library.infrastructure.adapter.input.web.dto.ApiErrorV1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** The Class Global Exception Handler. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle isbn exception.
     *
     * @param ex the exception.
     * @return the response entity.
     */
    @ExceptionHandler(IsbnException.class)
    public ResponseEntity<ApiErrorV1> handleIsbnException(final IsbnException ex) {
        final var apiErrorV1 = ApiErrorV1.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorV1);
    }

    /**
     * Handle exception.
     *
     * @param ex the exception.
     * @return the response entity.
     */
    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<ApiErrorV1> handleException(final LibraryException ex) {
        final var apiErrorV1 = ApiErrorV1.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorV1);
    }
}
