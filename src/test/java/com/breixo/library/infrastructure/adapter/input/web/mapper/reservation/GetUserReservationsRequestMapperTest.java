package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
