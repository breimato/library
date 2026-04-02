package com.breixo.library.domain.port.output.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Request Creation Persistence Port. */
public interface LoanRequestCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createLoanRequestCommand the create loan request command.
     * @return the created loan request.
     */
    LoanRequest execute(@Valid @NotNull CreateLoanRequestCommand createLoanRequestCommand);
}
