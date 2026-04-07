package com.breixo.library.domain.port.output.fine;

import java.util.List;

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
     * @return the list of fines.
     */
    List<Fine> find(@Valid @NotNull FineSearchCriteriaCommand fineSearchCriteriaCommand);

    /**
     * Find by user id.
     *
     * @param userId the user id.
     * @return the list of fines.
     */
    List<Fine> findByUserId(@NotNull Integer userId);
}
