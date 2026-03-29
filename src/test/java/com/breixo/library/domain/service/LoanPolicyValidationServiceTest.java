package com.breixo.library.domain.service;

import java.util.List;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
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
                () -> this.loanPolicyValidationService.checkCanBorrow(blockedUser, book, List.of()));

        // Then
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when user is suspended then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenUserIsSuspended_thenThrowLoanException() {

        // Given
        final var user = Instancio.create(User.class);
        final var suspendedUser = User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .status(UserStatus.SUSPENDED)
                .build();

        final var book = Instancio.create(Book.class);

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(suspendedUser, book, List.of()));

        // Then
        assertEquals(ExceptionMessageConstants.USER_SUSPENDED_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when user has overdue loans then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenUserHasOverdueLoans_thenThrowLoanException() {

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

        final var loan = Instancio.create(Loan.class);
        final var overdueLoan = Loan.builder()
                .id(loan.id())
                .userId(loan.userId())
                .bookId(loan.bookId())
                .dueDate(loan.dueDate())
                .returnDate(loan.returnDate())
                .status(LoanStatus.OVERDUE)
                .build();

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, book, List.of(overdueLoan)));

        // Then
        assertEquals(ExceptionMessageConstants.USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when user has reached loan limit then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenUserHasReachedLoanLimit_thenThrowLoanException() {

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

        final var loan1 = Instancio.create(Loan.class);
        final var activeLoan1 = Loan.builder()
                .id(loan1.id())
                .userId(loan1.userId())
                .bookId(loan1.bookId())
                .dueDate(loan1.dueDate())
                .returnDate(loan1.returnDate())
                .status(LoanStatus.ACTIVE)
                .build();

        final var loan2 = Instancio.create(Loan.class);
        final var activeLoan2 = Loan.builder()
                .id(loan2.id())
                .userId(loan2.userId())
                .bookId(loan2.bookId())
                .dueDate(loan2.dueDate())
                .returnDate(loan2.returnDate())
                .status(LoanStatus.ACTIVE)
                .build();

        final var loan3 = Instancio.create(Loan.class);
        final var activeLoan3 = Loan.builder()
                .id(loan3.id())
                .userId(loan3.userId())
                .bookId(loan3.bookId())
                .dueDate(loan3.dueDate())
                .returnDate(loan3.returnDate())
                .status(LoanStatus.ACTIVE)
                .build();

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, book,
                        List.of(activeLoan1, activeLoan2, activeLoan3)));

        // Then
        assertEquals(ExceptionMessageConstants.USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when user already has book on loan then throw loan exception.
     */
    @Test
    void testCheckCanBorrow_whenUserAlreadyHasBookOnLoan_thenThrowLoanException() {

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

        final var loan = Instancio.create(Loan.class);
        final var activeLoanForSameBook = Loan.builder()
                .id(loan.id())
                .userId(loan.userId())
                .bookId(book.id())
                .dueDate(loan.dueDate())
                .returnDate(loan.returnDate())
                .status(LoanStatus.ACTIVE)
                .build();

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, book, List.of(activeLoanForSameBook)));

        // Then
        assertEquals(ExceptionMessageConstants.USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR, loanException.getMessage());
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
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, retiredBook, List.of()));

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
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, unavailableBook, List.of()));

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
        assertDoesNotThrow(() -> this.loanPolicyValidationService.checkCanBorrow(activeUser, availableBook, List.of()));
    }
}
