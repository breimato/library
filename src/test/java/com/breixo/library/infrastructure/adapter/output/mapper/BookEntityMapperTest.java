package com.breixo.library.infrastructure.adapter.output.mapper;

import com.breixo.library.domain.command.book.CreateBookCommand;
import com.breixo.library.infrastructure.adapter.output.entities.BookEntity;
import com.breixo.library.infrastructure.mapper.IsbnMapperImpl;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Book Entity Mapper Test. */
@ExtendWith(MockitoExtension.class)
class BookEntityMapperTest {

    /** The Constant VALID_ISBN. */
    static final String VALID_ISBN = "9780134685991";

    /** The book entity mapper. */
    @InjectMocks
    BookEntityMapperImpl bookEntityMapper;

    /** The isbn mapper. */
    @Spy
    IsbnMapperImpl isbnMapper;

    /**
     * Test to book when book entity is valid then return mapped book.
     */
    @Test
    void testToBook_whenBookEntityIsValid_thenReturnMappedBook() {
        // Given
        final var bookEntity = Instancio.create(BookEntity.class);
        bookEntity.setIsbn(VALID_ISBN);

        // When
        final var book = this.bookEntityMapper.toBook(bookEntity);

        // Then
        assertNotNull(book);
        assertEquals(bookEntity.getId(), book.id());
        assertEquals(bookEntity.getIsbn(), book.isbn().getValue());
        assertEquals(bookEntity.getTitle(), book.title());
        assertEquals(bookEntity.getAuthor(), book.author());
        assertEquals(bookEntity.getGenre(), book.genre());
        assertEquals(bookEntity.getTotalCopies(), book.totalCopies());
        assertEquals(bookEntity.getAvailableCopies(), book.availableCopies());
    }

    /**
     * Test to book when book entity is null then return null.
     */
    @Test
    void testToBook_whenBookEntityIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.bookEntityMapper.toBook(null));
    }

    /**
     * Test to book entity when create book command is valid then return mapped book entity.
     */
    @Test
    void testToBookEntity_whenCreateBookCommandIsValid_thenReturnMappedBookEntity() {
        // Given
        final var createBookCommand = Instancio.create(CreateBookCommand.class);

        // When
        final var bookEntity = this.bookEntityMapper.toBookEntity(createBookCommand);

        // Then
        assertNotNull(bookEntity);
        assertEquals(createBookCommand.isbn().getValue(), bookEntity.getIsbn());
        assertEquals(createBookCommand.title(), bookEntity.getTitle());
        assertEquals(createBookCommand.author(), bookEntity.getAuthor());
        assertEquals(createBookCommand.genre(), bookEntity.getGenre());
        assertEquals(createBookCommand.totalCopies(), bookEntity.getTotalCopies());
        assertEquals(createBookCommand.availableCopies(), bookEntity.getAvailableCopies());
    }

    /**
     * Test to book entity when create book command is null then return null.
     */
    @Test
    void testToBookEntity_whenCreateBookCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.bookEntityMapper.toBookEntity(null));
    }
}
