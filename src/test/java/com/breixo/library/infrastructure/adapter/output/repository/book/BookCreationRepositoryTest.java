package com.breixo.library.infrastructure.adapter.output.repository.book;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.BookMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Book Creation Repository Test. */
@ExtendWith(MockitoExtension.class)
class BookCreationRepositoryTest {

    /** The book creation repository. */
    @InjectMocks
    BookCreationRepository bookCreationRepository;

    /** The book my batis mapper. */
    @Mock
    BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    @Mock
    BookEntityMapper bookEntityMapper;

    /**
     * Test execute when command is valid then return created book.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnCreatedBook() {
        
        // Given
        final var createBookCommand = Instancio.create(CreateBookCommand.class);
        final var bookEntity = Instancio.create(BookEntity.class);
        final var createdBookEntity = Instancio.create(BookEntity.class);
        final var book = Instancio.create(Book.class);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(bookEntity.getId()).build();

        // When
        when(this.bookEntityMapper.toBookEntity(createBookCommand)).thenReturn(bookEntity);
        when(this.bookMyBatisMapper.find(bookSearchCriteriaCommand)).thenReturn(List.of(createdBookEntity));
        when(this.bookEntityMapper.toBook(createdBookEntity)).thenReturn(book);
        final var result = this.bookCreationRepository.execute(createBookCommand);

        // Then
        verify(this.bookEntityMapper, times(1)).toBookEntity(createBookCommand);
        verify(this.bookMyBatisMapper, times(1)).insert(bookEntity);
        verify(this.bookMyBatisMapper, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookEntityMapper, times(1)).toBook(createdBookEntity);
        assertEquals(book, result);
    }

    /**
     * Test execute when insert throws exception then throw book exception.
     */
    @Test
    void testExecute_whenInsertThrowsException_thenThrowBookException() {
        
        // Given
        final var createBookCommand = Instancio.create(CreateBookCommand.class);
        final var bookEntity = Instancio.create(BookEntity.class);

        // When
        when(this.bookEntityMapper.toBookEntity(createBookCommand)).thenReturn(bookEntity);
        doThrow(new RuntimeException()).when(this.bookMyBatisMapper).insert(bookEntity);
        final var bookException = assertThrows(BookException.class,
                () -> this.bookCreationRepository.execute(createBookCommand));

        // Then
        verify(this.bookEntityMapper, times(1)).toBookEntity(createBookCommand);
        verify(this.bookMyBatisMapper, times(1)).insert(bookEntity);
        assertEquals(ExceptionMessageConstants.BOOK_CREATION_ERROR_CODE_ERROR, bookException.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_CREATION_ERROR_MESSAGE_ERROR, bookException.getMessage());
    }
}
