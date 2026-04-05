package com.breixo.library.infrastructure.adapter.input.web.handler;

import java.util.Set;

import com.breixo.library.domain.exception.AuthorizationException;
import com.breixo.library.domain.exception.IsbnException;
import com.breixo.library.domain.exception.LibraryException;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.infrastructure.adapter.input.web.dto.ApiErrorV1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** The Class Global Exception Handler. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** The Constant LOAN_POLICY_CODES. */
    private static final Set<String> LOAN_POLICY_CODES = Set.of(
            ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
            ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_CODE_ERROR,
            ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR
    );

    /**
     * Handle authorization exception.
     *
     * @param ex the exception.
     * @return the response entity.
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiErrorV1> handleAuthorizationException(final AuthorizationException ex) {
        final var apiErrorV1 = ApiErrorV1.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        final var status = ExceptionMessageConstants.AUTH_INVALID_CREDENTIALS_CODE_ERROR.equals(ex.getCode())
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(apiErrorV1);
    }

    /**
     * Handle loan exception.
     *
     * @param ex the exception.
     * @return the response entity.
     */
    @ExceptionHandler(LoanException.class)
    public ResponseEntity<ApiErrorV1> handleLoanException(final LoanException ex) {
        final var apiErrorV1 = ApiErrorV1.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        final var status = LOAN_POLICY_CODES.contains(ex.getCode())
                ? HttpStatus.BAD_REQUEST
                : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(apiErrorV1);
    }

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
