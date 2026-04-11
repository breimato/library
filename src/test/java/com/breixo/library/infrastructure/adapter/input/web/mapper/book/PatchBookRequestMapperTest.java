package com.breixo.library.infrastructure.adapter.input.web.mapper.book;

import com.breixo.library.infrastructure.adapter.input.web.dto.PatchBookV1Request;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        final var requesterId = Instancio.create(Integer.class);
        final var id = Instancio.create(Integer.class);
        final var patchBookV1Request = Instancio.create(PatchBookV1Request.class);

        // When
        final var updateBookCommand = this.patchBookRequestMapper.toUpdateBookCommand(id, requesterId, patchBookV1Request);

        // Then
        assertNotNull(updateBookCommand);
        assertEquals(id, updateBookCommand.id());
        assertEquals(requesterId, updateBookCommand.requesterId());
        assertEquals(patchBookV1Request.getTitle(), updateBookCommand.title());
        assertEquals(patchBookV1Request.getAuthor(), updateBookCommand.author());
        assertEquals(patchBookV1Request.getGenre(), updateBookCommand.genre());
        assertEquals(patchBookV1Request.getTotalCopies(), updateBookCommand.totalCopies());
        assertEquals(patchBookV1Request.getAvailableCopies(), updateBookCommand.availableCopies());
    }

    /**
     * Test to update book command when all params are null then return null.
     */
    @Test
    void testToUpdateBookCommand_whenAllParamsAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.patchBookRequestMapper.toUpdateBookCommand(null, null, null));
    }

    /**
     * Test to update book command when id is null then return command with null id.
     */
    @Test
    void testToUpdateBookCommand_whenIdIsNull_thenReturnCommandWithNullId() {
        
        // Given
        final var requesterId = Instancio.create(Integer.class);
        final var patchBookV1Request = Instancio.create(PatchBookV1Request.class);

        // When
        final var updateBookCommand = this.patchBookRequestMapper.toUpdateBookCommand(null, requesterId, patchBookV1Request);

        // Then
        assertNotNull(updateBookCommand);
        assertNull(updateBookCommand.id());
        assertEquals(requesterId, updateBookCommand.requesterId());
        assertEquals(patchBookV1Request.getTitle(), updateBookCommand.title());
        assertEquals(patchBookV1Request.getAuthor(), updateBookCommand.author());
        assertEquals(patchBookV1Request.getGenre(), updateBookCommand.genre());
        assertEquals(patchBookV1Request.getTotalCopies(), updateBookCommand.totalCopies());
        assertEquals(patchBookV1Request.getAvailableCopies(), updateBookCommand.availableCopies());
    }

    /**
     * Test to update book command when request is null then return command with only id.
     */
    @Test
    void testToUpdateBookCommand_whenRequestIsNull_thenReturnCommandWithOnlyId() {
        
        // Given
        final var requesterId = Instancio.create(Integer.class);
        final var id = Instancio.create(Integer.class);

        // When
        final var updateBookCommand = this.patchBookRequestMapper.toUpdateBookCommand(id, requesterId, null);

        // Then
        assertNotNull(updateBookCommand);
        assertEquals(id, updateBookCommand.id());
        assertEquals(requesterId, updateBookCommand.requesterId());
        assertNull(updateBookCommand.title());
        assertNull(updateBookCommand.author());
        assertNull(updateBookCommand.genre());
        assertNull(updateBookCommand.totalCopies());
        assertNull(updateBookCommand.availableCopies());
    }

    /**
     * Test to update book command when id and requester id are null and request is not null then return command.
     */
    @Test
    void testToUpdateBookCommand_whenIdAndRequesterIdAreNullAndRequestIsNotNull_thenReturnCommand() {

        // Given
        final var patchBookV1Request = Instancio.create(PatchBookV1Request.class);

        // When
        final var updateBookCommand = this.patchBookRequestMapper
                .toUpdateBookCommand(null, null, patchBookV1Request);

        // Then
        assertNotNull(updateBookCommand);
        assertNull(updateBookCommand.id());
        assertNull(updateBookCommand.requesterId());
        assertEquals(patchBookV1Request.getTitle(), updateBookCommand.title());
        assertEquals(patchBookV1Request.getAuthor(), updateBookCommand.author());
    }
}
