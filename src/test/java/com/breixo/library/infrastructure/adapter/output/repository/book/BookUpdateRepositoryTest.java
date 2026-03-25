package com.breixo.library.infrastructure.adapter.output.repository.book;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.UpdateBookCommand;
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

/** The Class Book Update Repository Test. */
@ExtendWith(MockitoExtension.class)
class BookUpdateRepositoryTest {

    /** The book update persistence repository. */
    @InjectMocks
    BookUpdatePersistenceRepository bookUpdatePersistenceRepository;

    /** The book my batis mapper. */
    @Mock
    BookMyBatisMapper bookMyBatisMapper;

    /** The book entity mapper. */
    @Mock
    BookEntityMapper bookEntityMapper;

    /**
     * Test execute when book exists then return updated book.
     */
    @Test
    void testExecute_whenBookExists_thenReturnUpdatedBook() {
        // Given
        final var updateBookCommand = Instancio.create(UpdateBookCommand.class);
        final var bookEntity = Instancio.create(BookEntity.class);
        final var book = Instancio.create(Book.class);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(updateBookCommand.id()).build();

        // When
        when(this.bookMyBatisMapper.find(bookSearchCriteriaCommand)).thenReturn(List.of(bookEntity));
        when(this.bookEntityMapper.toBook(bookEntity)).thenReturn(book);
        final var result = this.bookUpdatePersistenceRepository.execute(updateBookCommand);

        // Then
        verify(this.bookMyBatisMapper, times(1)).update(updateBookCommand);
        verify(this.bookMyBatisMapper, times(1)).find(bookSearchCriteriaCommand);
        verify(this.bookEntityMapper, times(1)).toBook(bookEntity);
        assertEquals(book, result);
    }

    /**
     * Test execute when update throws exception then throw book exception.
     */
    @Test
    void testExecute_whenUpdateThrowsException_thenThrowBookException() {
        // Given
        final var updateBookCommand = Instancio.create(UpdateBookCommand.class);

        // When
        doThrow(new RuntimeException()).when(this.bookMyBatisMapper).update(updateBookCommand);
        final var bookException = assertThrows(BookException.class,
                () -> this.bookUpdatePersistenceRepository.execute(updateBookCommand));

        // Then
        verify(this.bookMyBatisMapper, times(1)).update(updateBookCommand);
        assertEquals(ExceptionMessageConstants.BOOK_UPDATE_ERROR_CODE_ERROR, bookException.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_UPDATE_ERROR_MESSAGE_ERROR, bookException.getMessage());
    }
}
