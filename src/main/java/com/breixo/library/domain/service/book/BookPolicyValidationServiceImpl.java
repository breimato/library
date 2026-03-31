package com.breixo.library.domain.service.book;

import com.breixo.library.domain.port.input.book.BookPolicyValidationService;
import org.springframework.stereotype.Service;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;

import jakarta.validation.constraints.NotNull;

/** The Class Book Policy Validation Service Impl. */
@Service
public class BookPolicyValidationServiceImpl implements BookPolicyValidationService {

    /** {@inheritDoc} */
    @Override
    public void checkIsBorrowable(@NotNull final Book book) {

        if (book.totalCopies().equals(0)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR);
        }

        if (book.availableCopies().equals(0)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_COPIES_NOT_AVAILABLE_MESSAGE_ERROR);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void checkIsReservable(@NotNull final Book book) {

        if (book.totalCopies().equals(0)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR);
        }

        if (book.availableCopies() > 0) {
            throw new BookException(
                    ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_CODE_ERROR,
                    ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_MESSAGE_ERROR);
        }
    }
}
