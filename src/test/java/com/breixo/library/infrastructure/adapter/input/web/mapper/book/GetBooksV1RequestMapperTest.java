package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.infrastructure.adapter.input.web.dto.GetBooksV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Books V1 Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetBooksV1RequestMapperTest {

    /** The get books V1 request mapper. */
    @InjectMocks
    GetBooksV1RequestMapperImpl getBooksV1RequestMapper;

    /**
     * Test to book search criteria command when request is valid then return mapped command.
     */
    @Test
    void testToBookSearchCriteriaCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var isbn = Instancio.create(String.class);
        final var title = Instancio.create(String.class);
        final var author = Instancio.create(String.class);
        final var genre = Instancio.create(String.class);
        final var getBooksV1Request = Instancio.create(GetBooksV1Request.class);
        getBooksV1Request.setId(id);
        getBooksV1Request.setIsbn(isbn);
        getBooksV1Request.setTitle(title);
        getBooksV1Request.setAuthor(author);
        getBooksV1Request.setGenre(genre);

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(getBooksV1Request);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertEquals(id, bookSearchCriteriaCommand.getId());
        assertEquals(isbn, bookSearchCriteriaCommand.getIsbn());
        assertEquals(title, bookSearchCriteriaCommand.getTitle());
        assertEquals(author, bookSearchCriteriaCommand.getAuthor());
        assertEquals(genre, bookSearchCriteriaCommand.getGenre());
    }

    /**
     * Test to book search criteria command when request is null then return default command.
     */
    @Test
    void testToBookSearchCriteriaCommand_whenRequestIsNull_thenReturnDefaultCommand() {

        // When
        final var bookSearchCriteriaCommand = this.getBooksV1RequestMapper.toBookSearchCriteriaCommand(null);

        // Then
        assertNotNull(bookSearchCriteriaCommand);
        assertNull(bookSearchCriteriaCommand.getId());
        assertNull(bookSearchCriteriaCommand.getIsbn());
        assertNull(bookSearchCriteriaCommand.getTitle());
        assertNull(bookSearchCriteriaCommand.getAuthor());
        assertNull(bookSearchCriteriaCommand.getGenre());
    }
}
