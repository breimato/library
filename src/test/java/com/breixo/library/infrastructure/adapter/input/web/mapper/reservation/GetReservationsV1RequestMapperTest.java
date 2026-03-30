package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.infrastructure.adapter.input.web.dto.GetReservationsV1Request;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Reservations V1 Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class GetReservationsV1RequestMapperTest {

    /** The get reservations V1 request mapper. */
    @InjectMocks
    GetReservationsV1RequestMapperImpl getReservationsV1RequestMapper;

    /**
     * Test to reservation search criteria command when request is valid then return mapped command.
     */
    @Test
    void testToReservationSearchCriteriaCommand_whenRequestIsValid_thenReturnMappedCommand() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var loanId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);
        final var getReservationsV1Request = Instancio.create(GetReservationsV1Request.class);
        getReservationsV1Request.setId(id);
        getReservationsV1Request.setUserId(userId);
        getReservationsV1Request.setBookId(bookId);
        getReservationsV1Request.setLoanId(loanId);
        getReservationsV1Request.setStatus(status);

        // When
        final var reservationSearchCriteriaCommand =
                this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(getReservationsV1Request);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertEquals(id, reservationSearchCriteriaCommand.getId());
        assertEquals(userId, reservationSearchCriteriaCommand.getUserId());
        assertEquals(bookId, reservationSearchCriteriaCommand.getBookId());
        assertEquals(loanId, reservationSearchCriteriaCommand.getLoanId());
        assertEquals(status, reservationSearchCriteriaCommand.getStatusId());
    }

    /**
     * Test to reservation search criteria command when request is null then return default command.
     */
    @Test
    void testToReservationSearchCriteriaCommand_whenRequestIsNull_thenReturnDefaultCommand() {

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertNull(reservationSearchCriteriaCommand.getId());
        assertNull(reservationSearchCriteriaCommand.getUserId());
        assertNull(reservationSearchCriteriaCommand.getBookId());
        assertNull(reservationSearchCriteriaCommand.getLoanId());
        assertNull(reservationSearchCriteriaCommand.getStatusId());
    }
}
