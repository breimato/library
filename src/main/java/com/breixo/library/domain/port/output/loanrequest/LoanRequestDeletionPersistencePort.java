package com.breixo.library.domain.port.output.loanrequest;

import jakarta.validation.constraints.NotNull;

/** The Interface Loan Request Deletion Persistence Port. */
public interface LoanRequestDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the loan request identifier.
     */
    void execute(@NotNull Integer id);
}
