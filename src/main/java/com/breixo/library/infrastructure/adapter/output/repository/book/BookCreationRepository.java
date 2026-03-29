package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.CreateBookCommand;
import com.breixo.library.domain.port.output.book.BookCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Book Creation repository. */
@Component
@RequiredArgsConstructor
public class BookCreationRepository implements BookCreationPersistencePort {

    /** The book my batis mapper. */
    private final BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    private final BookEntityMapper bookEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Book execute(@Valid @NotNull final CreateBookCommand createBookCommand) {
        final var bookEntity = this.insert(createBookCommand);
        return this.find(bookEntity.getId());
    }

    /**
     * Insert.
     *
     * @param createBookCommand the create book command.
     * @return the book entity.
     */
    private BookEntity insert(final CreateBookCommand createBookCommand) {

        final var bookEntity = this.bookEntityMapper.toBookEntity(createBookCommand);

        try {
            this.bookMyBatisMapper.insert(bookEntity);

        } catch (final Exception exception) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_CREATION_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_CREATION_ERROR_MESSAGE_ERROR);
        }
        return bookEntity;
    }

    /**
     * Find.
     *
     * @param id the book identifier.
     * @return the book.
     */
    private Book find(final Integer id) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();

        final var bookEntity = this.bookMyBatisMapper.find(bookSearchCriteriaCommand).getFirst();

        return this.bookEntityMapper.toBook(bookEntity);
    }
}
