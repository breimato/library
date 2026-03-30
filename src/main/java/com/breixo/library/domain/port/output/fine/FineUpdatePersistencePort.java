package com.breixo.library.domain.port.output.fine;

import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.Fine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Fine Update Persistence Port. */
public interface FineUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param updateFineCommand the update fine command.
     * @return the updated fine.
     */
    Fine execute(@Valid @NotNull UpdateFineCommand updateFineCommand);
}
