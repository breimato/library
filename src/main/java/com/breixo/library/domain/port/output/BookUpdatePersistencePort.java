package com.breixo.library.domain.port.output;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.UpdateBookCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Book Update Persistence Port. */
public interface BookUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param updateBookCommand the update book command.
     * @return the updated book.
     */
    Book execute(@Valid @NotNull UpdateBookCommand updateBookCommand);
}
