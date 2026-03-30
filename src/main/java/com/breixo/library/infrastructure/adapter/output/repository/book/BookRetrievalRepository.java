package com.breixo.library.infrastructure.adapter.output.repository.book;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/** The Class Book Retrieval repository. */
@Component
@RequiredArgsConstructor
public class BookRetrievalRepository implements BookRetrievalPersistencePort {

    /** The book my batis mapper. */
    private final BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    private final BookEntityMapper bookEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Book findById(@NotNull final Integer id) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();
        final var bookList = this.find(bookSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(bookList)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }

        return bookList.getFirst();
    }

    /** {@inheritDoc} */
    @Override
    public List<Book> find(@Valid @NotNull final BookSearchCriteriaCommand bookSearchCriteriaCommand) {

        final var bookEntities = this.bookMyBatisMapper.find(bookSearchCriteriaCommand);

        return this.bookEntityMapper.toBookList(bookEntities);
    }
}
