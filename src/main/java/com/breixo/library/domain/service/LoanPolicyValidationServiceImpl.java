package com.breixo.library.domain.service;

import java.util.List;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/** The Class Loan Policy Validation Service Impl. */
@Component
public class LoanPolicyValidationServiceImpl implements LoanPolicyValidationService {

    /** The Constant MAX_ACTIVE_LOANS. */
    private static final int MAX_ACTIVE_LOANS = 3;

    /** {@inheritDoc} */
    @Override
    public void checkCanBorrow(@Valid @NotNull final User user, @Valid @NotNull final Book book,
            @NotNull final List<Loan> loanList) {

        if (UserStatus.BLOCKED.getId().equals(user.status().getId())) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                    ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);
        }
        if (UserStatus.SUSPENDED.getId().equals(user.status().getId())) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_SUSPENDED_CODE_ERROR,
                    ExceptionMessageConstants.USER_SUSPENDED_MESSAGE_ERROR);
        }
        if (loanList.stream().anyMatch(loan -> LoanStatus.OVERDUE.getId().equals(loan.status().getId()))) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_HAS_OVERDUE_LOANS_CODE_ERROR,
                    ExceptionMessageConstants.USER_HAS_OVERDUE_LOANS_MESSAGE_ERROR);
        }
        if (loanList.stream().filter(loan -> LoanStatus.ACTIVE.getId().equals(loan.status().getId())).count() >= MAX_ACTIVE_LOANS) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR,
                    ExceptionMessageConstants.USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR);
        }
        if (loanList.stream().anyMatch(loan -> loan.bookId().equals(book.id())
                && LoanStatus.ACTIVE.getId().equals(loan.status().getId()))) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_ALREADY_HAS_BOOK_ON_LOAN_CODE_ERROR,
                    ExceptionMessageConstants.USER_ALREADY_HAS_BOOK_ON_LOAN_MESSAGE_ERROR);
        }
        if (book.totalCopies().equals(0)) {
            throw new LoanException(
                    ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR);
        }
        if (book.availableCopies().equals(0)) {
            throw new LoanException(
                    ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_MESSAGE_ERROR);
        }
    }
}
