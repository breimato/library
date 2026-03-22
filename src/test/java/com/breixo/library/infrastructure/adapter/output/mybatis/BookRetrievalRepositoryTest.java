package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.FindBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.repository.BookRetrievalPersistenceRepository;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Book Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class BookRetrievalRepositoryTest {

    /** The book retrieval persistence repository. */
    @InjectMocks
    BookRetrievalPersistenceRepository bookRetrievalPersistenceRepository;

    /** The book my batis mapper. */
    @Mock
    BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    @Mock
    BookEntityMapper bookEntityMapper;

    /**
     * Test execute when books found then return book list.
     */
    @Test
    void testExecute_whenBooksFound_thenReturnBookList() {
        // Given
        final var findBookCommand = Instancio.create(FindBookCommand.class);
        final var bookEntity = Instancio.create(BookEntity.class);
        final var book = Instancio.create(Book.class);

        // When
        when(this.bookMyBatisMapper.find(findBookCommand)).thenReturn(List.of(bookEntity));
        when(this.bookEntityMapper.toBook(bookEntity)).thenReturn(book);
        final var result = this.bookRetrievalPersistenceRepository.execute(findBookCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(findBookCommand);
        verify(this.bookEntityMapper, times(1)).toBook(bookEntity);
        assertEquals(List.of(book), result);
    }

    /**
     * Test execute when no books found then return empty list.
     */
    @Test
    void testExecute_whenNoBooksFound_thenReturnEmptyList() {
        // Given
        final var findBookCommand = Instancio.create(FindBookCommand.class);

        // When
        when(this.bookMyBatisMapper.find(findBookCommand)).thenReturn(List.of());
        final var result = this.bookRetrievalPersistenceRepository.execute(findBookCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(findBookCommand);
        assertTrue(result.isEmpty());
    }
}
