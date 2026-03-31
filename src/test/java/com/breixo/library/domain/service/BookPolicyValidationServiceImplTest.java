package com.breixo.library.domain.service;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;

/** The Class Book Policy Validation Service Impl Test. */
@ExtendWith(MockitoExtension.class)
class BookPolicyValidationServiceImplTest {

    @InjectMocks
    BookPolicyValidationServiceImpl bookPolicyValidationServiceImpl;

    /** Test check is borrowable when book retired then throw book exception. */
    @Test
    void testCheckIsBorrowable_whenBookRetired_thenThrowBookException() {

        // Given
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "totalCopies"), 0)
                .set(field(Book.class, "availableCopies"), 0)
                .create();

        // When/Then
        final var exception = assertThrows(BookException.class,
                () -> this.bookPolicyValidationServiceImpl.checkIsBorrowable(book));
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check is borrowable when no copies available then throw book exception. */
    @Test
    void testCheckIsBorrowable_whenNoCopiesAvailable_thenThrowBookException() {

        // Given
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "totalCopies"), 5)
                .set(field(Book.class, "availableCopies"), 0)
                .create();

        // When/Then
        final var exception = assertThrows(BookException.class,
                () -> this.bookPolicyValidationServiceImpl.checkIsBorrowable(book));
        assertEquals(ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check is borrowable when copies available then does not throw. */
    @Test
    void testCheckIsBorrowable_whenCopiesAvailable_thenDoesNotThrow() {

        // Given
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "totalCopies"), 5)
                .set(field(Book.class, "availableCopies"), 3)
                .create();

        // When/Then
        assertDoesNotThrow(() -> this.bookPolicyValidationServiceImpl.checkIsBorrowable(book));
    }

    /** Test check is reservable when book retired then throw book exception. */
    @Test
    void testCheckIsReservable_whenBookRetired_thenThrowBookException() {

        // Given
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "totalCopies"), 0)
                .set(field(Book.class, "availableCopies"), 0)
                .create();

        // When/Then
        final var exception = assertThrows(BookException.class,
                () -> this.bookPolicyValidationServiceImpl.checkIsReservable(book));
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check is reservable when copies available then throw book exception. */
    @Test
    void testCheckIsReservable_whenCopiesAvailable_thenThrowBookException() {

        // Given
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "totalCopies"), 5)
                .set(field(Book.class, "availableCopies"), 3)
                .create();

        // When/Then
        final var exception = assertThrows(BookException.class,
                () -> this.bookPolicyValidationServiceImpl.checkIsReservable(book));
        assertEquals(ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check is reservable when no copies available then does not throw. */
    @Test
    void testCheckIsReservable_whenNoCopiesAvailable_thenDoesNotThrow() {

        // Given
        final var book = Instancio.of(Book.class)
                .set(field(Book.class, "totalCopies"), 5)
                .set(field(Book.class, "availableCopies"), 0)
                .create();

        // When/Then
        assertDoesNotThrow(() -> this.bookPolicyValidationServiceImpl.checkIsReservable(book));
    }
}
