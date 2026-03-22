package com.breixo.library.infrastructure.adapter.output.repository;

import java.util.Objects;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.UpdateBookCommand;
import com.breixo.library.domain.port.output.BookUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

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
    public Book execute(final UpdateBookCommand updateBookCommand) {
        final var bookEntity = this.bookMyBatisMapper.findById(updateBookCommand.id());
        if (Objects.isNull(bookEntity)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }
        this.bookMyBatisMapper.update(updateBookCommand);
        final var updatedBookEntity = this.bookMyBatisMapper.findById(updateBookCommand.id());
        return this.bookEntityMapper.toBook(updatedBookEntity);
    }
}
