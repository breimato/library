package com.breixo.library.infrastructure.adapter.output.repository;

import java.util.List;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.FindBookCommand;
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
    public List<Book> execute(final FindBookCommand findBookCommand) {
        final var bookEntities = this.bookMyBatisMapper.find(findBookCommand);
        return bookEntities.stream().map(this.bookEntityMapper::toBook).toList();
    }
}
