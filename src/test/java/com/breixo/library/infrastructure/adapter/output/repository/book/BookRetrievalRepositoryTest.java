package com.breixo.library.infrastructure.adapter.output.repository.book;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

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
     * Test execute when book found then return book.
     */
    @Test
    void testExecute_whenBookFound_thenReturnBook() {
        // Given
        final var bookSearchCriteriaCommand = Instancio.create(BookSearchCriteriaCommand.class);
        final var bookEntity = Instancio.create(BookEntity.class);
        final var book = Instancio.create(Book.class);

        // When
        when(this.bookMyBatisMapper.find(bookSearchCriteriaCommand)).thenReturn(List.of(bookEntity));
        when(this.bookEntityMapper.toBook(bookEntity)).thenReturn(book);
        final var result = this.bookRetrievalPersistenceRepository.execute(bookSearchCriteriaCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookEntityMapper, times(1)).toBook(bookEntity);
        assertEquals(Optional.of(book), result);
    }

    /**
     * Test execute when book not found then return empty optional.
     */
    @Test
    void testExecute_whenBookNotFound_thenReturnEmptyOptional() {
        // Given
        final var bookSearchCriteriaCommand = Instancio.create(BookSearchCriteriaCommand.class);

        // When
        when(this.bookMyBatisMapper.find(bookSearchCriteriaCommand)).thenReturn(List.of());
        final var result = this.bookRetrievalPersistenceRepository.execute(bookSearchCriteriaCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(bookSearchCriteriaCommand);
        assertTrue(result.isEmpty());
    }
}
