package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Get Reservations V1 Request Mapper Test. */
class GetReservationsV1RequestMapperTest {

    /** The get Reservations V1 request mapper. */
    final GetReservationsV1RequestMapper getReservationsV1RequestMapper = new GetReservationsV1RequestMapperImpl();

    /** Test mapping when AllFilters then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenAllFilters_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var loanId = Instancio.create(Integer.class);
        final var status = Instancio.create(Integer.class);

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(id, userId, bookId, loanId, status);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertEquals(id, reservationSearchCriteriaCommand.getId());
        assertEquals(userId, reservationSearchCriteriaCommand.getUserId());
        assertEquals(bookId, reservationSearchCriteriaCommand.getBookId());
        assertEquals(loanId, reservationSearchCriteriaCommand.getLoanId());
        assertEquals(status, reservationSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when NoFilters then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenNoFilters_thenReturnMatchingCriteria() {

        // Given

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null, null, null, null, null);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertNull(reservationSearchCriteriaCommand.getId());
        assertNull(reservationSearchCriteriaCommand.getUserId());
        assertNull(reservationSearchCriteriaCommand.getBookId());
        assertNull(reservationSearchCriteriaCommand.getLoanId());
        assertNull(reservationSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Id then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenId_thenReturnMatchingCriteria() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(id, null, null, null, null);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertEquals(id, reservationSearchCriteriaCommand.getId());
        assertNull(reservationSearchCriteriaCommand.getUserId());
        assertNull(reservationSearchCriteriaCommand.getBookId());
        assertNull(reservationSearchCriteriaCommand.getLoanId());
        assertNull(reservationSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when UserId then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenUserId_thenReturnMatchingCriteria() {

        // Given
        final var userId = Instancio.create(Integer.class);

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null, userId, null, null, null);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertNull(reservationSearchCriteriaCommand.getId());
        assertEquals(userId, reservationSearchCriteriaCommand.getUserId());
        assertNull(reservationSearchCriteriaCommand.getBookId());
        assertNull(reservationSearchCriteriaCommand.getLoanId());
        assertNull(reservationSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when BookId then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenBookId_thenReturnMatchingCriteria() {

        // Given
        final var bookId = Instancio.create(Integer.class);

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null, null, bookId, null, null);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertNull(reservationSearchCriteriaCommand.getId());
        assertNull(reservationSearchCriteriaCommand.getUserId());
        assertEquals(bookId, reservationSearchCriteriaCommand.getBookId());
        assertNull(reservationSearchCriteriaCommand.getLoanId());
        assertNull(reservationSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when LoanId then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenLoanId_thenReturnMatchingCriteria() {

        // Given
        final var loanId = Instancio.create(Integer.class);

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null, null, null, loanId, null);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertNull(reservationSearchCriteriaCommand.getId());
        assertNull(reservationSearchCriteriaCommand.getUserId());
        assertNull(reservationSearchCriteriaCommand.getBookId());
        assertEquals(loanId, reservationSearchCriteriaCommand.getLoanId());
        assertNull(reservationSearchCriteriaCommand.getStatusId());
    }

    /** Test mapping when Status then return matching criteria. */
    @Test
    void testToReservationSearchCriteriaCommand_whenStatus_thenReturnMatchingCriteria() {

        // Given
        final var status = Instancio.create(Integer.class);

        // When
        final var reservationSearchCriteriaCommand = this.getReservationsV1RequestMapper.toReservationSearchCriteriaCommand(null, null, null, null, status);

        // Then
        assertNotNull(reservationSearchCriteriaCommand);
        assertNull(reservationSearchCriteriaCommand.getId());
        assertNull(reservationSearchCriteriaCommand.getUserId());
        assertNull(reservationSearchCriteriaCommand.getBookId());
        assertNull(reservationSearchCriteriaCommand.getLoanId());
        assertEquals(status, reservationSearchCriteriaCommand.getStatusId());
    }
}
