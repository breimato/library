package com.breixo.library.domain.service.loan;

import java.util.List;

import com.breixo.library.domain.port.input.loan.LoanPolicyValidationService;
import org.springframework.stereotype.Component;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;

import jakarta.validation.constraints.NotNull;

/** The Class Loan Policy Validation Service Impl. */
@Component
public class LoanPolicyValidationServiceImpl implements LoanPolicyValidationService {

    /** The Constant MAX_ACTIVE_LOANS. */
    private static final int MAX_ACTIVE_LOANS = 3;

    /** {@inheritDoc} */
    @Override
    public void checkCanBorrow(@NotNull final Book book, @NotNull final List<Loan> loanList) {

        this.validateUserLoans(loanList);

        this.validateUserDoesNotHaveBookOnLoan(book, loanList);
    }

    /**
     * Validate user loans.
     *
     * @param loanList the loan list
     */
    private void validateUserLoans(final List<Loan> loanList) {

        final var hasOverdueLoans = loanList.stream()
                .anyMatch(loan -> LoanStatus.OVERDUE.getId().equals(loan.status().getId()));

        if (hasOverdueLoans) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_USER_HAS_OVERDUE_LOANS_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR);
        }

        final var hasReachedLoanLimit = loanList.stream()
                .filter(loan -> LoanStatus.ACTIVE.getId().equals(loan.status().getId()))
                .count() >= MAX_ACTIVE_LOANS;

        if (hasReachedLoanLimit) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR);
        }
    }

    /**
     * Validate user does not have book on loan.
     *
     * @param book     the book
     * @param loanList the loan list
     */
    private void validateUserDoesNotHaveBookOnLoan(final Book book, final List<Loan> loanList) {

        final var hasActiveLoanForThisBook = loanList.stream()
                .anyMatch(loan -> loan.bookId().equals(book.id())
                        && LoanStatus.ACTIVE.getId().equals(loan.status().getId()));

        if (hasActiveLoanForThisBook) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR);
        }
    }
}
