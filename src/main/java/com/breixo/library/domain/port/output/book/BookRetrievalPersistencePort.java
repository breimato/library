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
     * Find.
     *
     * @param bookSearchCriteriaCommand the book search criteria command.
     * @return the book, or empty if not found.
     */
    Optional<Book> find(@Valid @NotNull BookSearchCriteriaCommand bookSearchCriteriaCommand);

    /**
     * Find all.
     *
     * @param bookSearchCriteriaCommand the book search criteria command.
     * @return the list of books matching the criteria, or all books if no criteria provided.
     */
    List<Book> findAll(@Valid @NotNull BookSearchCriteriaCommand bookSearchCriteriaCommand);
}
