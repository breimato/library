package com.breixo.library.domain.port.output.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Request Update Persistence Port. */
public interface LoanRequestUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param updateLoanRequestCommand the update loan request command.
     * @return the updated loan request.
     */
    LoanRequest execute(@Valid @NotNull UpdateLoanRequestCommand updateLoanRequestCommand);
}
