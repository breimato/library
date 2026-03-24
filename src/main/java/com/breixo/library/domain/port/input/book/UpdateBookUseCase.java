package com.breixo.library.domain.port.input.book;

import com.breixo.library.domain.command.book.UpdateBookCommand;
import com.breixo.library.domain.model.book.Book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Update Book Use Case. */
public interface UpdateBookUseCase {

    /**
     * Execute.
     *
     * @param updateBookCommand the update book command.
     * @return the updated book.
     */
    Book execute(@Valid @NotNull UpdateBookCommand updateBookCommand);
}
