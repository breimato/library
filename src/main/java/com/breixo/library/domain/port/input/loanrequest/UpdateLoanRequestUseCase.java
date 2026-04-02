package com.breixo.library.domain.port.input.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Update Loan Request Use Case. */
public interface UpdateLoanRequestUseCase {

    /**
     * Execute.
     *
     * @param updateLoanRequestCommand the update loan request command.
     * @return the updated loan request.
     */
    LoanRequest execute(@Valid @NotNull UpdateLoanRequestCommand updateLoanRequestCommand);
}
