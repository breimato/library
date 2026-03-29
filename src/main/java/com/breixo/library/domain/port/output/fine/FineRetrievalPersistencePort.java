package com.breixo.library.domain.port.output.fine;

import java.util.Optional;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.model.fine.Fine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Fine Retrieval Persistence Port. */
public interface FineRetrievalPersistencePort {

    /**
     * Find.
     *
     * @param fineSearchCriteriaCommand the fine search criteria command.
     * @return the fine, or empty if not found.
     */
    Optional<Fine> find(@Valid @NotNull FineSearchCriteriaCommand fineSearchCriteriaCommand);
}
