package com.breixo.library.domain.port.input.loan;

import com.breixo.library.domain.command.loan.LoanReturnCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Update Loan Return Use Case. */
public interface UpdateLoanReturnUseCase {

    /**
     * Execute.
     *
     * @param loanReturnCommand the loan return command
     * @return the loan
     */
    Loan execute(@Valid @NotNull LoanReturnCommand loanReturnCommand);
}
