package com.breixo.library.infrastructure.adapter.output.repository.book;

import com.breixo.library.domain.port.output.book.BookDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Book Deletion Persistence Repository. */
@Component
@RequiredArgsConstructor
public class BookDeletionPersistenceRepository implements BookDeletionPersistencePort {

    /** The book my batis mapper. */
    private final BookMyBatisMapper bookMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public void execute(@NotNull final Long id) {
        this.bookMyBatisMapper.delete(id);
    }
}
