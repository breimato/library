package com.breixo.library.domain.port.output;

import com.breixo.library.domain.model.Book;

/** The Interface Book Retrieval Persistence Port. */
public interface BookRetrievalPersistencePort {

    /**
     * Find by id.
     *
     * @param id the id.
     * @return the book.
     */
    Book findById(Long id);
}
