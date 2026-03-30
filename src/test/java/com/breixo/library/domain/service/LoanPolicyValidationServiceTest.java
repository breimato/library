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
import static org.instancio.Select.field;
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
        assertEquals(ExceptionMessageConstants.LOAN_USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR, loanException.getMessage());
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

        final var loanList = Instancio.ofList(Loan.class)
                .size(3)
                .set(field(Loan.class, "status"), LoanStatus.ACTIVE)
                .create();

        // When
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationService.checkCanBorrow(activeUser, book, loanList));

        // Then
        assertEquals(ExceptionMessageConstants.LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR, loanException.getMessage());
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
        assertEquals(ExceptionMessageConstants.LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test check can borrow when user had same book but loan is not active then no exception.
     */
    @Test
    void testCheckCanBorrow_whenUserHadSameBookButLoanIsNotActive_thenNoException() {

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

        final var loan = Instancio.create(Loan.class);
        final var returnedLoanForSameBook = Loan.builder()
                .id(loan.id())
                .userId(loan.userId())
                .bookId(availableBook.id())
                .dueDate(loan.dueDate())
                .returnDate(loan.returnDate())
                .status(LoanStatus.RETURNED)
                .build();

        // When / Then
        assertDoesNotThrow(() -> this.loanPolicyValidationService
                .checkCanBorrow(activeUser, availableBook, List.of(returnedLoanForSameBook)));
    }

    /**
     * Test check can borrow when user has active loan for different book then no exception.
     */
    @Test
    void testCheckCanBorrow_whenUserHasActiveLoanForDifferentBook_thenNoException() {

        // Given
        final var user = Instancio.create(User.class);
        final var activeUser = User.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .phone(user.phone())
                .status(UserStatus.ACTIVE)
                .build();

        final var loan = Instancio.create(Loan.class);
        final var activeLoanForDifferentBook = Loan.builder()
                .id(loan.id())
                .userId(loan.userId())
                .bookId(loan.bookId())
                .dueDate(loan.dueDate())
                .returnDate(loan.returnDate())
                .status(LoanStatus.ACTIVE)
                .build();

        final var book = Instancio.create(Book.class);
        final var availableBook = Book.builder()
                .id(book.id().equals(activeLoanForDifferentBook.bookId()) ? book.id() + 1 : book.id())
                .isbn(book.isbn())
                .title(book.title())
                .author(book.author())
                .genre(book.genre())
                .totalCopies(book.totalCopies() > 0 ? book.totalCopies() : 1)
                .availableCopies(book.availableCopies() > 0 ? book.availableCopies() : 1)
                .build();

        // When / Then
        assertDoesNotThrow(() -> this.loanPolicyValidationService
                .checkCanBorrow(activeUser, availableBook, List.of(activeLoanForDifferentBook)));
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
