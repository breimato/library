package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Request;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/** The Class Patch Book Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PatchBookRequestMapperTest {

    /** The patch book request mapper. */
    @InjectMocks
    PatchBookRequestMapperImpl patchBookRequestMapper;

    /**
     * Test to update book command when request is valid then return mapped command.
     */
    @Test
    void testToUpdateBookCommand_whenRequestIsValid_thenReturnMappedCommand() {
        // Given
        final var id = Instancio.create(Long.class);
        final var patchBookV1Request = Instancio.create(PatchBookV1Request.class);

        // When
        final var updateBookCommand = this.patchBookRequestMapper.toUpdateBookCommand(id, patchBookV1Request);

        // Then
        assertNotNull(updateBookCommand);
        assertEquals(id, updateBookCommand.id());
        assertEquals(patchBookV1Request.getTitle(), updateBookCommand.title());
        assertEquals(patchBookV1Request.getAuthor(), updateBookCommand.author());
        assertEquals(patchBookV1Request.getGenre(), updateBookCommand.genre());
        assertEquals(patchBookV1Request.getTotalCopies(), updateBookCommand.totalCopies());
        assertEquals(patchBookV1Request.getAvailableCopies(), updateBookCommand.availableCopies());
    }
}
