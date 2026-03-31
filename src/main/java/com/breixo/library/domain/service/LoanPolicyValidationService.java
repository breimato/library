package com.breixo.library.domain.service;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Policy Validation Service. */
public interface LoanPolicyValidationService {

    /**
     * Check can borrow.
     *
     * @param book     the book
     * @param loanList the loan list
     */
    void checkCanBorrow(@NotNull Book book, @NotNull List<Loan> loanList);
}
