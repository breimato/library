package com.breixo.library.domain.port.output;

import java.util.List;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.FindBookCommand;

/** The Interface Book Retrieval Persistence Port. */
public interface BookRetrievalPersistencePort {

    /**
     * Execute.
     *
     * @param findBookCommand the find book command.
     * @return the list of books.
     */
    List<Book> execute(FindBookCommand findBookCommand);
}
