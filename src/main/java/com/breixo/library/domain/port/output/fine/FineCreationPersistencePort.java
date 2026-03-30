package com.breixo.library.domain.port.output.fine;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.model.fine.Fine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Fine Creation Persistence Port. */
public interface FineCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createFineCommand the create fine command.
     * @return the created fine.
     */
    Fine execute(@Valid @NotNull CreateFineCommand createFineCommand);
}
