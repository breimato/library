package com.breixo.library.domain.port.output.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Retrieval Persistence Port. */
public interface LoanRetrievalPersistencePort {

    /**
     * Find.
     *
     * @param loanSearchCriteriaCommand the loan search criteria command
     * @return the list of loans
     */
    List<Loan> find(@Valid @NotNull LoanSearchCriteriaCommand loanSearchCriteriaCommand);
}
