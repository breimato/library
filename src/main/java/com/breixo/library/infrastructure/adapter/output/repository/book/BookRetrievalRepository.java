package com.breixo.library.infrastructure.adapter.output.repository.book;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
    public Optional<Book> findById(@NotNull final Integer id) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(id).build();
        final var bookList = this.find(bookSearchCriteriaCommand);

        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList.getFirst());
    }

    /** {@inheritDoc} */
    @Override
    public List<Book> find(@Valid @NotNull final BookSearchCriteriaCommand bookSearchCriteriaCommand) {

        final var bookEntities = this.bookMyBatisMapper.find(bookSearchCriteriaCommand);

        return this.bookEntityMapper.toBookList(bookEntities);
    }
}
