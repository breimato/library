package com.breixo.library.infrastructure.adapter.input.web.handler;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.IsbnException;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/** The Class Global Exception Handler Test. */
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    /** The global exception handler. */
    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    /**
     * Test handle loan exception when code is policy code then return bad request.
     */
    @Test
    void testHandleLoanException_whenCodeIsPolicyCode_thenReturnBadRequest() {
        
        // Given
        final var loanException = new LoanException(
                ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);

        // When
        final var responseEntity = this.globalExceptionHandler.handleLoanException(loanException);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR, responseEntity.getBody().getCode());
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, responseEntity.getBody().getMessage());
    }

    /**
     * Test handle loan exception when code is not found code then return not found.
     */
    @Test
    void testHandleLoanException_whenCodeIsNotFoundCode_thenReturnNotFound() {
        
        // Given
        final var loanException = new LoanException(
                ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR,
                ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR);

        // When
        final var responseEntity = this.globalExceptionHandler.handleLoanException(loanException);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR, responseEntity.getBody().getCode());
        assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR, responseEntity.getBody().getMessage());
    }

    /**
     * Test handle isbn exception when called then return bad request.
     */
    @Test
    void testHandleIsbnException_whenCalled_thenReturnBadRequest() {
        
        // Given
        final var isbnException = new IsbnException(
                ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR);

        // When
        final var responseEntity = this.globalExceptionHandler.handleIsbnException(isbnException);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR, responseEntity.getBody().getCode());
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR, responseEntity.getBody().getMessage());
    }

    /**
     * Test handle exception when called then return not found.
     */
    @Test
    void testHandleException_whenCalled_thenReturnNotFound() {
        
        // Given
        final var bookException = new BookException(
                ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);

        // When
        final var responseEntity = this.globalExceptionHandler.handleException(bookException);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR, responseEntity.getBody().getCode());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, responseEntity.getBody().getMessage());
    }
}
