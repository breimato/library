package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
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

/** The Class Post Book Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostBookRequestMapperTest {

    /** The Constant VALID_ISBN. */
    static final String VALID_ISBN = "9780134685991";

    /** The post book request mapper. */
    @InjectMocks
    PostBookRequestMapperImpl postBookRequestMapper;

    /** The isbn mapper. */
    @Spy
    IsbnMapperImpl isbnMapper;

    /**
     * Test to create book command when request is valid then return mapped command.
     */
    @Test
    void testToCreateBookCommand_whenRequestIsValid_thenReturnMappedCommand() {
        // Given
        final var postBookV1Request = Instancio.create(PostBookV1Request.class);
        postBookV1Request.setIsbn(VALID_ISBN);

        // When
        final var createBookCommand = this.postBookRequestMapper.toCreateBookCommand(postBookV1Request);

        // Then
        assertNotNull(createBookCommand);
        assertEquals(postBookV1Request.getIsbn(), createBookCommand.isbn().getValue());
        assertEquals(postBookV1Request.getTitle(), createBookCommand.title());
        assertEquals(postBookV1Request.getAuthor(), createBookCommand.author());
        assertEquals(postBookV1Request.getGenre(), createBookCommand.genre());
        assertEquals(postBookV1Request.getTotalCopies(), createBookCommand.totalCopies());
        assertEquals(postBookV1Request.getAvailableCopies(), createBookCommand.availableCopies());
    }

    /**
     * Test to create book command when request is null then return null.
     */
    @Test
    void testToCreateBookCommand_whenRequestIsNull_thenReturnNull() {
        assertNull(this.postBookRequestMapper.toCreateBookCommand(null));
    }
}
