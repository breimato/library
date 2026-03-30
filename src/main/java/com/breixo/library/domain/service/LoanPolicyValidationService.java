package com.breixo.library.domain.service;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Policy Validation Service. */
public interface LoanPolicyValidationService {

    /**
     * Check can borrow.
     *
     * @param user  the user
     * @param book  the book
     * @param loanList the loan list
     */
    void checkCanBorrow(@NotNull User user, @NotNull Book book, @NotNull List<Loan> loanList);

    /**
     * Check user has no pending fines.
     *
     * @param loanList the loan list
     */
    void checkUserHasNoPendingFines(@NotNull List<Loan> loanList);
}
