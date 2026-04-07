package com.breixo.library.domain.port.output.loanrequest;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Request Retrieval Persistence Port. */
public interface LoanRequestRetrievalPersistencePort {

    /**
     * Find by id.
     *
     * @param id the loan request identifier.
     * @return the loan request, or empty if not found.
     */
    Optional<LoanRequest> findById(@NotNull Integer id);

    /**
     * Find.
     *
     * @param loanRequestSearchCriteriaCommand the loan request search criteria command.
     * @return the list of loan requests.
     */
    List<LoanRequest> find(@Valid @NotNull LoanRequestSearchCriteriaCommand loanRequestSearchCriteriaCommand);
}
