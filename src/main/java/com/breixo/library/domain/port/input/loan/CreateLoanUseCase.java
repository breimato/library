package com.breixo.library.domain.port.input.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Create Loan Use Case. */
public interface CreateLoanUseCase {

    /**
     * Execute.
     *
     * @param createLoanCommand the create loan command
     * @return the loan
     */
    Loan execute(@Valid @NotNull CreateLoanCommand createLoanCommand);
}
