package com.breixo.library.domain.port.output;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.UpdateBookCommand;

/** The Interface Book Update Persistence Port. */
public interface BookUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param updateBookCommand the update book command.
     * @return the updated book.
     */
    Book execute(UpdateBookCommand updateBookCommand);
}
