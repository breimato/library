package com.breixo.library.infrastructure.adapter.output.mybatis;

import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.exception.BookException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Book Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class BookRetrievalRepositoryTest {

    /** The book persistence adapter. */
    @InjectMocks
    BookRetrievalPersistenceRepository bookRetrievalPersistenceRepository;

    /** The book my batis mapper. */
    @Mock
    BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    @Mock
    BookEntityMapper bookEntityMapper;

    /**
     * Test find by id when book exists then return book.
     */
    @Test
    void testFindById_whenBookExists_thenReturnBook() {
        // Given
        final var id = Instancio.create(Long.class);
        final var bookEntity = Instancio.create(BookEntity.class);
        final var book = Instancio.create(Book.class);

        // When
        when(this.bookMyBatisMapper.findById(id)).thenReturn(bookEntity);
        when(this.bookEntityMapper.toBook(bookEntity)).thenReturn(book);
        final var result = this.bookRetrievalPersistenceRepository.findById(id);

        // Then
        verify(this.bookMyBatisMapper, times(1)).findById(id);
        verify(this.bookEntityMapper, times(1)).toBook(bookEntity);
        assertEquals(book, result);
    }

    /**
     * Test find by id when book not found then throw book not found exception.
     */
    @Test
    void testFindById_whenBookNotFound_thenThrowBookNotFoundException() {

        // Given
        final var id = Instancio.create(Long.class);

        // When
        when(this.bookMyBatisMapper.findById(id)).thenReturn(null);
        final var bookNotFoundException = assertThrows(BookException.class, () -> bookRetrievalPersistenceRepository.findById(id));

        // Then
        verify(this.bookMyBatisMapper, times(1)).findById(id);
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, bookNotFoundException.getMessage());
    }
}
