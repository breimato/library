package com.breixo.library.domain.port.output.book;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Book Retrieval Persistence Port. */
public interface BookRetrievalPersistencePort {

    /**
     * Find by id.
     *
     * @param id the id.
     * @return the book, or empty if not found.
     */
    Optional<Book> findById(@NotNull Integer id);

    /**
     * Find.
     *
     * @param bookSearchCriteriaCommand the book search criteria command.
     * @return the list of books.
     */
    List<Book> find(@Valid @NotNull BookSearchCriteriaCommand bookSearchCriteriaCommand);
}
