package com.breixo.library.domain.service;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.breixo.library.domain.service.loan.LoanPolicyValidationServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;

/** The Class Loan Policy Validation Service Test. */
@ExtendWith(MockitoExtension.class)
class LoanPolicyValidationServiceTest {

    @InjectMocks
    LoanPolicyValidationServiceImpl loanPolicyValidationServiceImpl;

    /** Test check can borrow when user has overdue loans then throw loan exception. */
    @Test
    void testCheckCanBorrow_whenUserHasOverdueLoans_thenThrowLoanException() {

        // Given
        final var book = Instancio.create(Book.class);
        final var overdueLoan = Instancio.of(Loan.class)
                .set(field(Loan.class, "status"), LoanStatus.OVERDUE)
                .create();

        // When/Then
        final var exception = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationServiceImpl.checkCanBorrow(book, List.of(overdueLoan)));
        assertEquals(ExceptionMessageConstants.LOAN_USER_HAS_OVERDUE_LOANS_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check can borrow when user has reached loan limit then throw loan exception. */
    @Test
    void testCheckCanBorrow_whenUserHasReachedLoanLimit_thenThrowLoanException() {

        // Given
        final var book = Instancio.create(Book.class);
        final var loanList = Instancio.ofList(Loan.class)
                .size(3)
                .set(field(Loan.class, "status"), LoanStatus.ACTIVE)
                .create();

        // When/Then
        final var exception = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationServiceImpl.checkCanBorrow(book, loanList));
        assertEquals(ExceptionMessageConstants.LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check can borrow when user already has book on loan then throw loan exception. */
    @Test
    void testCheckCanBorrow_whenUserAlreadyHasBookOnLoan_thenThrowLoanException() {

        // Given
        final var book = Instancio.create(Book.class);
        final var activeLoanForSameBook = Instancio.of(Loan.class)
                .set(field(Loan.class, "bookId"), book.id())
                .set(field(Loan.class, "status"), LoanStatus.ACTIVE)
                .create();

        // When/Then
        final var exception = assertThrows(LoanException.class,
                () -> this.loanPolicyValidationServiceImpl.checkCanBorrow(book, List.of(activeLoanForSameBook)));
        assertEquals(ExceptionMessageConstants.LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check can borrow when returned loan for same book then does not throw. */
    @Test
    void testCheckCanBorrow_whenReturnedLoanForSameBook_thenDoesNotThrow() {

        // Given
        final var book = Instancio.create(Book.class);
        final var returnedLoan = Instancio.of(Loan.class)
                .set(field(Loan.class, "bookId"), book.id())
                .set(field(Loan.class, "status"), LoanStatus.RETURNED)
                .create();

        // When/Then
        assertDoesNotThrow(() -> this.loanPolicyValidationServiceImpl.checkCanBorrow(book, List.of(returnedLoan)));
    }

    /** Test check can borrow when all conditions met then does not throw. */
    @Test
    void testCheckCanBorrow_whenAllConditionsMet_thenDoesNotThrow() {

        // Given
        final var book = Instancio.create(Book.class);

        // When/Then
        assertDoesNotThrow(() -> this.loanPolicyValidationServiceImpl.checkCanBorrow(book, List.of()));
    }
}
