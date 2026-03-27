package com.breixo.library.domain.service;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/** The Class Loan Policy Validation Service Impl. */
@Component
public class LoanPolicyValidationServiceImpl implements LoanPolicyValidationService {

    /** {@inheritDoc} */
    @Override
    public void checkCanBorrow(@Valid @NotNull final User user, @Valid @NotNull final Book book) {

        if (UserStatus.BLOCKED.getId().equals(user.status().getId())) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                    ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);
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
