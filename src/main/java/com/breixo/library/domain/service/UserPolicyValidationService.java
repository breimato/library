package com.breixo.library.domain.service;

import java.util.List;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import jakarta.validation.constraints.NotNull;

/** The Interface User Policy Validation Service. */
public interface UserPolicyValidationService {

    /**
     * Check.
     *
     * @param user     the user
     * @param loanList the loan list
     */
    void check(@NotNull User user, @NotNull List<Loan> loanList);
}
