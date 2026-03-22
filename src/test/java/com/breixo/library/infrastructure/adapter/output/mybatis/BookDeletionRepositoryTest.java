package com.breixo.library.infrastructure.adapter.output.mybatis;

import com.breixo.library.infrastructure.adapter.output.repository.BookDeletionPersistenceRepository;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** The Class Book Deletion Repository Test. */
@ExtendWith(MockitoExtension.class)
class BookDeletionRepositoryTest {

    /** The book deletion persistence repository. */
    @InjectMocks
    BookDeletionPersistenceRepository bookDeletionPersistenceRepository;

    /** The book my batis mapper. */
    @Mock
    BookMyBatisMapper bookMyBatisMapper;

    /**
     * Test execute when called then delete book.
     */
    @Test
    void testExecute_whenCalled_thenDeleteBook() {
        // Given
        final var id = Instancio.create(Long.class);

        // When
        this.bookDeletionPersistenceRepository.execute(id);

        // Then
        verify(this.bookMyBatisMapper, times(1)).delete(id);
    }
}
