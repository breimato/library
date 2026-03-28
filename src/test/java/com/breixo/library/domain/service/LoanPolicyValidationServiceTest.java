package com.breixo.library.domain.service;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserStatus;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** The Class Loan Policy Validation Service Test. */
@ExtendWith(MockitoExtension.class)
class LoanPolicyValidationServiceTest {

    /** The loan policy validation service. */
    @InjectMocks
    LoanPolicyValidationServiceImpl loanPolicyValidationService;

    /**
     * Test check can borrow when user is blocked then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenUserIsBlocked_thenThrowLoanException() {

        // Given
        final var user = Instancio.create(User.class);
        final var blockedUser = User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .status(UserStatus.BLOCKED)
                .build();

        final var book = Instancio.create(Book.class);

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(blockedUser, book));

        // Then
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when book is retired then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenBookIsRetired_thenThrowLoanException() {

        // Given
        final var user = Instancio.create(User.class);
        final var activeUser = User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .status(UserStatus.ACTIVE)
                .build();

        final var book = Instancio.create(Book.class);
        final var retiredBook = Book.builder()
                .id(book.id())
                .isbn(book.isbn())
                .title(book.title())
                .author(book.author())
                .genre(book.genre())
                .totalCopies(0)
                .availableCopies(0)
                .build();

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, retiredBook));

        // Then
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when no copies available then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenNoCopiesAvailable_thenThrowLoanException() {

        // Given
        final var user = Instancio.create(User.class);
        final var activeUser = User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .status(UserStatus.ACTIVE)
                .build();

        final var book = Instancio.create(Book.class);
        final var unavailableBook = Book.builder()
                .id(book.id())
                .isbn(book.isbn())
                .title(book.title())
                .author(book.author())
                .genre(book.genre())
                .totalCopies(book.totalCopies())
                .availableCopies(0)
                .build();

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, unavailableBook));

        // Then
        assertEquals(ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when all conditions met then no exception.
     */
    @Test
    void testCheckCanBorrow_whenAllConditionsMet_thenNoException() {

        // Given
        final var user = Instancio.create(User.class);
        final var activeUser = User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .status(UserStatus.ACTIVE)
                .build();

        final var book = Instancio.create(Book.class);
        final var availableBook = Book.builder()
                .id(book.id())
                .isbn(book.isbn())
                .title(book.title())
                .author(book.author())
                .genre(book.genre())
                .totalCopies(book.totalCopies() > 0 ? book.totalCopies() : 1)
                .availableCopies(book.availableCopies() > 0 ? book.availableCopies() : 1)
                .build();

        // When / Then
        assertDoesNotThrow(() -> this.loanPolicyValidationService.checkCanBorrow(activeUser, availableBook));
    }
}
