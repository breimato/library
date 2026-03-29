package com.breixo.library.infrastructure.adapter.output.repository.book;

import java.util.List;

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

    /** The book retrieval repository. */
    @InjectMocks
    BookRetrievalRepository bookRetrievalRepository;

    /** The book my batis mapper. */
    @Mock
    BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    @Mock
    BookEntityMapper bookEntityMapper;

    /**
     * Test find when criteria provided then return matching books.
     */
    @Test
    void testFind_whenCriteriaProvided_thenReturnMatchingBooks() {
        
        // Given
        final var bookSearchCriteriaCommand = Instancio.create(BookSearchCriteriaCommand.class);
        final var bookEntities = Instancio.createList(BookEntity.class);
        final var books = Instancio.createList(Book.class);

        // When
        when(this.bookMyBatisMapper.find(bookSearchCriteriaCommand)).thenReturn(bookEntities);
        when(this.bookEntityMapper.toBookList(bookEntities)).thenReturn(books);
        final var result = this.bookRetrievalRepository.find(bookSearchCriteriaCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookEntityMapper, times(1)).toBookList(bookEntities);
        assertEquals(books, result);
    }

    /**
     * Test find when no books match criteria then return empty list.
     */
    @Test
    void testFind_whenNoBooksMatchCriteria_thenReturnEmptyList() {
        
        // Given
        final var bookSearchCriteriaCommand = Instancio.create(BookSearchCriteriaCommand.class);
        final var books = List.<Book>of();

        // When
        when(this.bookMyBatisMapper.find(bookSearchCriteriaCommand)).thenReturn(List.of());
        when(this.bookEntityMapper.toBookList(List.of())).thenReturn(books);
        final var result = this.bookRetrievalRepository.find(bookSearchCriteriaCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookEntityMapper, times(1)).toBookList(List.of());
        assertTrue(result.isEmpty());
    }
}
