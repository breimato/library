package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostBookV1Request;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/** The Class Post Book Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostBookRequestMapperTest {

    /** The post book request mapper. */
    @InjectMocks
    PostBookRequestMapperImpl postBookRequestMapper;

    /**
     * Test to create book command when request is valid then return mapped command.
     */
    @Test
    void testToCreateBookCommand_whenRequestIsValid_thenReturnMappedCommand() {
        // Given
        final var postBookV1Request = Instancio.create(PostBookV1Request.class);

        // When
        final var createBookCommand = this.postBookRequestMapper.toCreateBookCommand(postBookV1Request);

        // Then
        assertNotNull(createBookCommand);
        assertEquals(postBookV1Request.getIsbn(), createBookCommand.isbn());
        assertEquals(postBookV1Request.getTitle(), createBookCommand.title());
        assertEquals(postBookV1Request.getAuthor(), createBookCommand.author());
        assertEquals(postBookV1Request.getGenre(), createBookCommand.genre());
        assertEquals(postBookV1Request.getTotalCopies(), createBookCommand.totalCopies());
        assertEquals(postBookV1Request.getAvailableCopies(), createBookCommand.availableCopies());
    }
}
