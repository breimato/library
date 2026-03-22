package com.breixo.library.infrastructure.adapter.output.mybatis;

import java.util.List;

import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.Book;
import com.breixo.library.domain.model.FindBookCommand;
import com.breixo.library.domain.model.UpdateBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.BookEntityMapper;
import com.breixo.library.infrastructure.adapter.output.repository.BookUpdatePersistenceRepository;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
        final var updatedBookEntity = Instancio.create(BookEntity.class);
        final var book = Instancio.create(Book.class);

        // When
        when(this.bookMyBatisMapper.find(any(FindBookCommand.class)))
                .thenReturn(List.of(bookEntity), List.of(updatedBookEntity));
        when(this.bookEntityMapper.toBook(updatedBookEntity)).thenReturn(book);
        final var result = this.bookUpdatePersistenceRepository.execute(updateBookCommand);

        // Then
        verify(this.bookMyBatisMapper, times(2)).find(any(FindBookCommand.class));
        verify(this.bookMyBatisMapper, times(1)).update(updateBookCommand);
        verify(this.bookEntityMapper, times(1)).toBook(updatedBookEntity);
        assertEquals(book, result);
    }

    /**
     * Test execute when book not found then throw book not found exception.
     */
    @Test
    void testExecute_whenBookNotFound_thenThrowBookNotFoundException() {
        // Given
        final var updateBookCommand = Instancio.create(UpdateBookCommand.class);

        // When
        when(this.bookMyBatisMapper.find(any(FindBookCommand.class))).thenReturn(List.of());
        final var bookException = assertThrows(BookException.class,
                () -> this.bookUpdatePersistenceRepository.execute(updateBookCommand));

        // Then
        verify(this.bookMyBatisMapper, times(1)).find(any(FindBookCommand.class));
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, bookException.getMessage());
    }
}
