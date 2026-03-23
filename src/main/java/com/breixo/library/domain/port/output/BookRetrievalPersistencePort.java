package com.breixo.library.domain.port.output;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Book Retrieval Persistence Port. */
public interface BookRetrievalPersistencePort {

    /**
     * Execute.
     *
     * @param bookSearchCriteriaCommand the book search criteria command.
     * @return the book.
     */
    Book execute(@Valid @NotNull BookSearchCriteriaCommand bookSearchCriteriaCommand);
}
