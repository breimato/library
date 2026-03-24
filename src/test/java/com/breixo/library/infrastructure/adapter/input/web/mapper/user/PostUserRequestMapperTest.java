package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1RequestDto;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Post User Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class PostUserRequestMapperTest {

    /** The post user request mapper. */
    @InjectMocks
    PostUserRequestMapperImpl postUserRequestMapper;

    /**
     * Test to create user command when request is valid then return mapped command.
     */
    @Test
    void testToCreateUserCommand_whenRequestIsValid_thenReturnMappedCommand() {
        // Given
        final var request = Instancio.create(PostUserV1RequestDto.class);

        // When
        final var command = this.postUserRequestMapper.toCreateUserCommand(request);

        // Then
        assertNotNull(command);
        assertEquals(request.getName(), command.name());
        assertEquals(request.getEmail(), command.email());
        assertEquals(request.getPhone(), command.phone());
    }

    /**
     * Test to create user command when request is null then return null.
     */
    @Test
    void testToCreateUserCommand_whenRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.postUserRequestMapper.toCreateUserCommand(null));
    }
}
