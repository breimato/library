package com.breixo.library.domain.port.input.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Create Loan Request Use Case. */
public interface CreateLoanRequestUseCase {

    /**
     * Execute.
     *
     * @param createLoanRequestCommand the create loan request command.
     * @return the created loan request.
     */
    LoanRequest execute(@Valid @NotNull CreateLoanRequestCommand createLoanRequestCommand);
}
