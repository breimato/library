package com.breixo.library.domain.port.output;

/** The Interface Book Deletion Persistence Port. */
public interface BookDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the book identifier.
     */
    void execute(Long id);
}
