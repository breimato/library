package com.breixo.library.domain.port.output;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.CreateBookCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Book Creation Persistence Port. */
public interface BookCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createBookCommand the create book command.
     * @return the created book.
     */
    Book execute(@Valid @NotNull CreateBookCommand createBookCommand);
}
