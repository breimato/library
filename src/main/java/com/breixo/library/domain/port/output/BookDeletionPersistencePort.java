package com.breixo.library.domain.port.output;

import jakarta.validation.constraints.NotNull;

/** The Interface Book Deletion Persistence Port. */
public interface BookDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the book identifier.
     */
    void execute(@NotNull Long id);
}
