package com.breixo.library.domain.service;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.user.User;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Policy Validation Service. */
public interface LoanPolicyValidationService {

    /**
     * Check can borrow.
     *
     * @param user the user
     * @param book the book
     */
    void checkCanBorrow(@NotNull User user, @NotNull Book book);
}
