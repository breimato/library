package com.breixo.library.domain.port.output.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Creation Persistence Port. */
public interface LoanCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createLoanCommand the create loan command
     * @return the loan
     */
    Loan execute(@Valid @NotNull CreateLoanCommand createLoanCommand);
}
