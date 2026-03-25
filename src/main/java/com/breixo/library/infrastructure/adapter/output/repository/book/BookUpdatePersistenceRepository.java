package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.UpdateBookCommand;
import com.breixo.library.domain.port.output.book.BookUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Book Update Persistence Repository. */
@Component
@RequiredArgsConstructor
public class BookUpdatePersistenceRepository implements BookUpdatePersistencePort {

    /** The book my batis mapper. */
    private final BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    private final BookEntityMapper bookEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Book execute(@Valid @NotNull final UpdateBookCommand updateBookCommand) {
        this.update(updateBookCommand);
        return this.find(updateBookCommand.id());
    }

    /**
     * Update.
     *
     * @param updateBookCommand the update book command.
     */
    private void update(final UpdateBookCommand updateBookCommand) {
        try {
            this.bookMyBatisMapper.update(updateBookCommand);
        } catch (final Exception exception) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_UPDATE_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_UPDATE_ERROR_MESSAGE_ERROR);
        }
    }

    /**
     * Find.
     *
     * @param id the book identifier.
     * @return the book.
     */
    private Book find(final Long id) {
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();
        final var bookEntity = this.bookMyBatisMapper.find(bookSearchCriteriaCommand).getFirst();
        return this.bookEntityMapper.toBook(bookEntity);
    }
}
