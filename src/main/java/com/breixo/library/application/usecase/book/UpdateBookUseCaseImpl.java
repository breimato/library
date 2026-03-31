package com.breixo.library.application.usecase.book;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.UpdateBookCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.port.input.book.UpdateBookUseCase;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.book.BookUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Update Book Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateBookUseCaseImpl implements UpdateBookUseCase {

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book update persistence port. */
    private final BookUpdatePersistencePort bookUpdatePersistencePort;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Book execute(@Valid @NotNull final UpdateBookCommand updateBookCommand) {

        this.validateBookExists(updateBookCommand);

        return this.bookUpdatePersistencePort.execute(updateBookCommand);
    }

    /**
     * Validate book exists.
     *
     * @param updateBookCommand the update book command
     */
    private void validateBookExists(final UpdateBookCommand updateBookCommand) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(updateBookCommand.id()).build();
        final var books = this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(books)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }
    }
}
