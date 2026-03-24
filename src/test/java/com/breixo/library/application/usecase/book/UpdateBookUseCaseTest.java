package com.breixo.library.application.usecase.book;

import java.util.Optional;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.book.UpdateBookCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.book.BookUpdatePersistencePort;

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

/** The Class Update Book Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateBookUseCaseTest {

    /** The update book use case. */
    @InjectMocks
    UpdateBookUseCaseImpl updateBookUseCase;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The book update persistence port. */
    @Mock
    BookUpdatePersistencePort bookUpdatePersistencePort;

    /**
     * Test execute when book exists then update and return book.
     */
    @Test
    void testExecute_whenBookExists_thenUpdateAndReturnBook() {
        // Given
        final var updateBookCommand = Instancio.create(UpdateBookCommand.class);
        final var existingBook = Instancio.create(Book.class);
        final var updatedBook = Instancio.create(Book.class);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(updateBookCommand.id()).build();

        // When
        when(this.bookRetrievalPersistencePort.execute(bookSearchCriteriaCommand)).thenReturn(Optional.of(existingBook));
        when(this.bookUpdatePersistencePort.execute(updateBookCommand)).thenReturn(updatedBook);
        final var result = this.updateBookUseCase.execute(updateBookCommand);

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).execute(bookSearchCriteriaCommand);
        verify(this.bookUpdatePersistencePort, times(1)).execute(updateBookCommand);
        assertEquals(updatedBook, result);
    }

    /**
     * Test execute when book not found then throw book exception.
     */
    @Test
    void testExecute_whenBookNotFound_thenThrowBookException() {
        // Given
        final var updateBookCommand = Instancio.create(UpdateBookCommand.class);
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder().id(updateBookCommand.id()).build();

        // When
        when(this.bookRetrievalPersistencePort.execute(bookSearchCriteriaCommand)).thenReturn(Optional.empty());
        final var exception = assertThrows(BookException.class,
                () -> this.updateBookUseCase.execute(updateBookCommand));

        // Then
        verify(this.bookRetrievalPersistencePort, times(1)).execute(bookSearchCriteriaCommand);
        verify(this.bookUpdatePersistencePort, times(0)).execute(updateBookCommand);
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }
}
