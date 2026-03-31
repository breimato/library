package com.breixo.library.domain.service;

import com.breixo.library.domain.model.book.Book;
import jakarta.validation.constraints.NotNull;

/** The Interface Book Policy Validation Service. */
public interface BookPolicyValidationService {

    /**
     * Check is borrowable.
     *
     * @param book the book
     */
    void checkIsBorrowable(@NotNull Book book);

    /**
     * Check is reservable.
     *
     * @param book the book
     */
    void checkIsReservable(@NotNull Book book);
}
