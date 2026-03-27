package com.breixo.library.domain.port.output.loan;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Update Persistence Port. */
public interface LoanUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param updateLoanReturnCommand the update loan return command
     * @return the loan
     */
    Loan execute(@Valid @NotNull UpdateLoanReturnCommand updateLoanReturnCommand);
}
