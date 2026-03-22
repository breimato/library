package com.breixo.library.infrastructure.adapter.output.repository;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.CreateBookCommand;
import com.breixo.library.domain.port.output.BookCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Book Creation Persistence Repository. */
@Component
@RequiredArgsConstructor
public class BookCreationPersistenceRepository implements BookCreationPersistencePort {

    /** The book my batis mapper. */
    private final BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    private final BookEntityMapper bookEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Book execute(final CreateBookCommand createBookCommand) {
        final var bookEntity = this.bookEntityMapper.toBookEntity(createBookCommand);
        this.bookMyBatisMapper.insert(bookEntity);
        final var createdBookEntity = this.bookMyBatisMapper.findById(bookEntity.getId());
        return this.bookEntityMapper.toBook(createdBookEntity);
    }
}
