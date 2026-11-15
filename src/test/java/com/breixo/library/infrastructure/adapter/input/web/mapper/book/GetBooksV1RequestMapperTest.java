package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Books V1 Request Mapper Test. */
class GetBooksV1RequestMapperTest {

    /** The get Books V1 request mapper. */
    final GetBooksV1RequestMapper getBooksV1RequestMapper = new GetBooksV1RequestMapperImpl();

    /** Test mapping when AllFilters then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenAllFilters_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var isbn = Instancio.create(String.class);
        final var title = Instancio.create(String.class);
        final var author = Instancio.create(String.class);
        final var genre = Instancio.create(String.class);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(id, isbn, title, author, genre);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertEquals(id, bookSearchCriteriaCommand.getId());
        assertEquals(isbn, bookSearchCriteriaCommand.getIsbn());
        assertEquals(title, bookSearchCriteriaCommand.getTitle());
        assertEquals(author, bookSearchCriteriaCommand.getAuthor());
        assertEquals(genre, bookSearchCriteriaCommand.getGenre());
    }

    /** Test mapping when NoFilters then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenNoFilters_thenReturnMatchingCriteria() {

        // Given

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null, null, null, null, null);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertNull(bookSearchCriteriaCommand.getId());
        assertNull(bookSearchCriteriaCommand.getIsbn());
        assertNull(bookSearchCriteriaCommand.getTitle());
        assertNull(bookSearchCriteriaCommand.getAuthor());
        assertNull(bookSearchCriteriaCommand.getGenre());
    }

    /** Test mapping when Id then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenId_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(id, null, null, null, null);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertEquals(id, bookSearchCriteriaCommand.getId());
        assertNull(bookSearchCriteriaCommand.getIsbn());
        assertNull(bookSearchCriteriaCommand.getTitle());
        assertNull(bookSearchCriteriaCommand.getAuthor());
        assertNull(bookSearchCriteriaCommand.getGenre());
    }

    /** Test mapping when Isbn then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenIsbn_thenReturnMatchingCriteria() {

        // Given
        final var isbn = Instancio.create(String.class);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null, isbn, null, null, null);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertNull(bookSearchCriteriaCommand.getId());
        assertEquals(isbn, bookSearchCriteriaCommand.getIsbn());
        assertNull(bookSearchCriteriaCommand.getTitle());
        assertNull(bookSearchCriteriaCommand.getAuthor());
        assertNull(bookSearchCriteriaCommand.getGenre());
    }

    /** Test mapping when Title then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenTitle_thenReturnMatchingCriteria() {

        // Given
        final var title = Instancio.create(String.class);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null, null, title, null, null);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertNull(bookSearchCriteriaCommand.getId());
        assertNull(bookSearchCriteriaCommand.getIsbn());
        assertEquals(title, bookSearchCriteriaCommand.getTitle());
        assertNull(bookSearchCriteriaCommand.getAuthor());
        assertNull(bookSearchCriteriaCommand.getGenre());
    }

    /** Test mapping when Author then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenAuthor_thenReturnMatchingCriteria() {

        // Given
        final var author = Instancio.create(String.class);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null, null, null, author, null);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertNull(bookSearchCriteriaCommand.getId());
        assertNull(bookSearchCriteriaCommand.getIsbn());
        assertNull(bookSearchCriteriaCommand.getTitle());
        assertEquals(author, bookSearchCriteriaCommand.getAuthor());
        assertNull(bookSearchCriteriaCommand.getGenre());
    }

    /** Test mapping when Genre then return matching criteria. */
    @Test
    void testToBookSearchCriteriaCommand_whenGenre_thenReturnMatchingCriteria() {

        // Given
        final var genre = Instancio.create(String.class);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null, null, null, null, genre);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertNull(bookSearchCriteriaCommand.getId());
        assertNull(bookSearchCriteriaCommand.getIsbn());
        assertNull(bookSearchCriteriaCommand.getTitle());
        assertNull(bookSearchCriteriaCommand.getAuthor());
        assertEquals(genre, bookSearchCriteriaCommand.getGenre());
    }
}
