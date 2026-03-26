package com.breixo.library.domain.port.input.loan;

import com.breixo.library.domain.command.loan.ReturnLoanCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Return Use Case. */
public interface LoanReturnUseCase {

    /**
     * Execute.
     *
     * @param returnLoanCommand the return loan command
     * @return the loan
     */
    Loan execute(@Valid @NotNull ReturnLoanCommand returnLoanCommand);
}
