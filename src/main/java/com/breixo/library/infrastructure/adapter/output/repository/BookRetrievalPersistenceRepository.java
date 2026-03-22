package com.breixo.library.infrastructure.adapter.output.repository;

import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.port.output.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/** The Class Book Retrieval Persistence Repository. */
@Component
@RequiredArgsConstructor
public class BookRetrievalPersistenceRepository implements BookRetrievalPersistencePort {

    /** The book my batis mapper. */
    private final BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    private final BookEntityMapper bookEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Book findById(final Long id) {
        final var bookEntity = this.bookMyBatisMapper.findById(id);
        if (Objects.isNull(bookEntity)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }
        return this.bookEntityMapper.toBook(bookEntity);
    }
}
