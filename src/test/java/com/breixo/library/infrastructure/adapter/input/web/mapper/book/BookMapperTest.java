package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import java.util.List;

import com.breixo.library.domain.model.book.Book;
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

/** The Class Book Mapper Test. */
@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    /** The book mapper. */
    @InjectMocks
    BookMapperImpl bookMapper;

    /** The isbn mapper. */
    @Spy
    IsbnMapperImpl isbnMapper;

    /**
     * Test to book v 1 when book is valid then return mapped dto.
     */
    @Test
    void testToBookV1_whenBookIsValid_thenReturnMappedDto() {
        
        // Given
        final var book = Instancio.create(Book.class);

        // When
        final var bookV1Dto = this.bookMapper.toBookV1(book);

        // Then
        assertNotNull(bookV1Dto);
        assertEquals(book.id(), bookV1Dto.getId());
        assertEquals(book.isbn().getValue(), bookV1Dto.getIsbn());
        assertEquals(book.title(), bookV1Dto.getTitle());
        assertEquals(book.author(), bookV1Dto.getAuthor());
        assertEquals(book.genre(), bookV1Dto.getGenre());
        assertEquals(book.totalCopies(), bookV1Dto.getTotalCopies());
        assertEquals(book.availableCopies(), bookV1Dto.getAvailableCopies());
    }

    /**
     * Test to book v 1 when book is null then return null.
     */
    @Test
    void testToBookV1_whenBookIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.bookMapper.toBookV1(null));
    }

    /**
     * Test to book v 1 list when books are valid then return mapped dto list.
     */
    @Test
    void testToBookV1List_whenBooksAreValid_thenReturnMappedDtoList() {
        
        // Given
        final var book = Instancio.create(Book.class);
        final var books = List.of(book);

        // When
        final var bookV1DtoList = this.bookMapper.toBookV1List(books);

        // Then
        assertNotNull(bookV1DtoList);
        assertEquals(1, bookV1DtoList.size());
        assertEquals(book.id(), bookV1DtoList.getFirst().getId());
    }

    /**
     * Test to book v 1 list when books list is empty then return empty list.
     */
    @Test
    void testToBookV1List_whenBooksListIsEmpty_thenReturnEmptyList() {
        // When
        final var bookV1DtoList = this.bookMapper.toBookV1List(List.of());

        // Then
        assertNotNull(bookV1DtoList);
        assertEquals(0, bookV1DtoList.size());
    }

    /**
     * Test to book v 1 list when books list is null then return null.
     */
    @Test
    void testToBookV1List_whenBooksListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.bookMapper.toBookV1List(null));
    }
}
