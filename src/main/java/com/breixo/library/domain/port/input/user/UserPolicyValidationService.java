package com.breixo.library.domain.port.input.user;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/** The Interface User Policy Validation Service. */
public interface UserPolicyValidationService {

    /**
     * Execute.
     *
     * @param user     the user
     * @param loanList the loan list
     */
    void execute(@NotNull User user, @NotNull List<Loan> loanList);
}
