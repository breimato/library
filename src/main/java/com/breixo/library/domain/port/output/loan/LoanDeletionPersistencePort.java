package com.breixo.library.domain.port.output.loan;

import jakarta.validation.constraints.NotNull;

/** The Interface Loan Deletion Persistence Port. */
public interface LoanDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the id
     */
    void execute(@NotNull Integer id);
}
