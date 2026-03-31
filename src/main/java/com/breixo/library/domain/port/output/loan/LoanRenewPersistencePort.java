package com.breixo.library.domain.port.output.loan;

import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Renew Persistence Port. */
public interface LoanRenewPersistencePort {

    /**
     * Execute.
     *
     * @param updateLoanRenewCommand the update loan renew command
     * @return the loan
     */
    Loan execute(@Valid @NotNull UpdateLoanRenewCommand updateLoanRenewCommand);
}
