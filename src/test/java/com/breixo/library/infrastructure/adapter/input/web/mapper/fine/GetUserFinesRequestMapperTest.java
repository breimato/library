package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get User Fines Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetUserFinesRequestMapperTest {

    /** The get user fines request mapper. */
    @InjectMocks
    GetUserFinesRequestMapperImpl getUserFinesRequestMapper;

    /**
     * Test to get user fines command when valid parameters then return mapped command.
     */
    @Test
    void testToGetUserFinesCommand_whenValidParameters_thenReturnMappedCommand() {

        // Given
        final var requesterId = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);

        // When
        final var getUserFinesCommand = this.getUserFinesRequestMapper.toGetUserFinesCommand(requesterId, userId);

        // Then
        assertNotNull(getUserFinesCommand);
        assertEquals(requesterId, getUserFinesCommand.requesterId());
        assertEquals(userId, getUserFinesCommand.userId());
    }

    /**
     * Test to get user fines command when both parameters are null then return null.
     */
    @Test
    void testToGetUserFinesCommand_whenBothParametersAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.getUserFinesRequestMapper.toGetUserFinesCommand(null, null));
    }

    /**
     * Test to get user fines command when requester id is null then return command with null requester id.
     */
    @Test
    void testToGetUserFinesCommand_whenRequesterIdIsNull_thenReturnCommandWithNullRequesterId() {

        // Given
        final var userId = Instancio.create(Integer.class);

        // When
        final var getUserFinesCommand = this.getUserFinesRequestMapper.toGetUserFinesCommand(null, userId);

        // Then
        assertNotNull(getUserFinesCommand);
        assertNull(getUserFinesCommand.requesterId());
        assertEquals(userId, getUserFinesCommand.userId());
    }

    /**
     * Test to get user fines command when user id is null then return command with null user id.
     */
    @Test
    void testToGetUserFinesCommand_whenUserIdIsNull_thenReturnCommandWithNullUserId() {

        // Given
        final var requesterId = Instancio.create(Integer.class);

        // When
        final var getUserFinesCommand = this.getUserFinesRequestMapper.toGetUserFinesCommand(requesterId, null);

        // Then
        assertNotNull(getUserFinesCommand);
        assertEquals(requesterId, getUserFinesCommand.requesterId());
        assertNull(getUserFinesCommand.userId());
    }
}
