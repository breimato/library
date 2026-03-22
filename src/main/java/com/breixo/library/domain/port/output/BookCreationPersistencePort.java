package com.breixo.library.domain.port.output;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.CreateBookCommand;

/** The Interface Book Creation Persistence Port. */
public interface BookCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createBookCommand the create book command.
     * @return the created book.
     */
    Book execute(CreateBookCommand createBookCommand);
}
