package com.breixo.library.domain.port.output.user;

import jakarta.validation.constraints.NotNull;

/** The Interface User Deletion Persistence Port. */
public interface UserDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the user identifier.
     */
    void execute(@NotNull Long id);
}
