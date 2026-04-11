package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get User Reservations Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetUserReservationsRequestMapperTest {

    /** The get user reservations request mapper. */
    @InjectMocks
    GetUserReservationsRequestMapperImpl getUserReservationsRequestMapper;

    /**
     * Test to get user reservations command when valid parameters then return mapped command.
     */
    @Test
    void testToGetUserReservationsCommand_whenValidParameters_thenReturnMappedCommand() {

        // Given
        final var requesterId = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);

        // When
        final var getUserReservationsCommand = this.getUserReservationsRequestMapper
                .toGetUserReservationsCommand(requesterId, userId);

        // Then
        assertNotNull(getUserReservationsCommand);
        assertEquals(requesterId, getUserReservationsCommand.requesterId());
        assertEquals(userId, getUserReservationsCommand.userId());
    }

    /**
     * Test to get user reservations command when both parameters are null then return null.
     */
    @Test
    void testToGetUserReservationsCommand_whenBothParametersAreNull_thenReturnNull() {
        // When / Then
        assertNull(this.getUserReservationsRequestMapper.toGetUserReservationsCommand(null, null));
    }

    /**
     * Test to get user reservations command when requester id is null then return command with null requester id.
     */
    @Test
    void testToGetUserReservationsCommand_whenRequesterIdIsNull_thenReturnCommandWithNullRequesterId() {

        // Given
        final var userId = Instancio.create(Integer.class);

        // When
        final var getUserReservationsCommand = this.getUserReservationsRequestMapper
                .toGetUserReservationsCommand(null, userId);

        // Then
        assertNotNull(getUserReservationsCommand);
        assertNull(getUserReservationsCommand.requesterId());
        assertEquals(userId, getUserReservationsCommand.userId());
    }

    /**
     * Test to get user reservations command when user id is null then return command with null user id.
     */
    @Test
    void testToGetUserReservationsCommand_whenUserIdIsNull_thenReturnCommandWithNullUserId() {

        // Given
        final var requesterId = Instancio.create(Integer.class);

        // When
        final var getUserReservationsCommand = this.getUserReservationsRequestMapper
                .toGetUserReservationsCommand(requesterId, null);

        // Then
        assertNotNull(getUserReservationsCommand);
        assertEquals(requesterId, getUserReservationsCommand.requesterId());
        assertNull(getUserReservationsCommand.userId());
    }
}
