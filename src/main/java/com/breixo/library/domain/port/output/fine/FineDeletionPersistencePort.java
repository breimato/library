package com.breixo.library.domain.port.output.fine;

import jakarta.validation.constraints.NotNull;

/** The Interface Fine Deletion Persistence Port. */
public interface FineDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the id
     */
    void execute(@NotNull Integer id);
}
