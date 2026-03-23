package com.breixo.library.infrastructure.adapter.output.repository;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.BookSearchCriteriaCommand;
import com.breixo.library.domain.port.output.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public Book execute(final BookSearchCriteriaCommand bookSearchCriteriaCommand) {
        final var bookEntities = this.bookMyBatisMapper.find(bookSearchCriteriaCommand);
        if (bookEntities.isEmpty()) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }
        return this.bookEntityMapper.toBook(bookEntities.get(0));
    }
}
