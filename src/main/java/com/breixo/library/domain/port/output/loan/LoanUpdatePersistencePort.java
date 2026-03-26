package com.breixo.library.domain.port.output.loan;

import com.breixo.library.domain.command.loan.ReturnLoanCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Update Persistence Port. */
public interface LoanUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param returnLoanCommand the return loan command
     * @return the loan
     */
    Loan execute(@Valid @NotNull ReturnLoanCommand returnLoanCommand);
}
