package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import java.time.OffsetDateTime;
import java.util.List;

import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.ReservationStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Reservation Mapper Test. */
@ExtendWith(MockitoExtension.class)
class ReservationMapperTest {

    /** The reservation mapper. */
    @InjectMocks
    ReservationMapperImpl reservationMapper;

    /** The reservation status mapper. */
    @Mock
    ReservationStatusMapper reservationStatusMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to reservation V1 when reservation is valid then return mapped dto.
     */
    @Test
    void testToReservationV1_whenReservationIsValid_thenReturnMappedDto() {

        // Given
        final var reservation = Instancio.create(Reservation.class);
        final var statusId = Instancio.create(Integer.class);
        final var expiresAt = Instancio.create(OffsetDateTime.class);

        // When
        when(this.reservationStatusMapper.toStatusId(reservation.status())).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(reservation.expiresAt())).thenReturn(expiresAt);
        final var reservationV1 = this.reservationMapper.toReservationV1(reservation);

        // Then
        verify(this.reservationStatusMapper, times(1)).toStatusId(reservation.status());
        verify(this.dateMapper, times(1)).toOffsetDateTime(reservation.expiresAt());
        assertNotNull(reservationV1);
        assertEquals(reservation.id(), reservationV1.getId());
        assertEquals(reservation.userId(), reservationV1.getUserId());
        assertEquals(reservation.bookId(), reservationV1.getBookId());
        assertEquals(reservation.loanId(), reservationV1.getLoanId());
        assertEquals(statusId, reservationV1.getStatus());
        assertEquals(expiresAt, reservationV1.getExpiresAt());
    }

    /**
     * Test to reservation V1 when reservation is null then return null.
     */
    @Test
    void testToReservationV1_whenReservationIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationMapper.toReservationV1(null));
    }

    /**
     * Test to reservation V1 list when reservations are valid then return mapped dto list.
     */
    @Test
    void testToReservationV1List_whenReservationsAreValid_thenReturnMappedDtoList() {

        // Given
        final var reservation = Instancio.create(Reservation.class);
        final var reservations = List.of(reservation);
        final var statusId = Instancio.create(Integer.class);
        final var expiresAt = Instancio.create(OffsetDateTime.class);

        // When
        when(this.reservationStatusMapper.toStatusId(reservation.status())).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(reservation.expiresAt())).thenReturn(expiresAt);
        final var reservationV1List = this.reservationMapper.toReservationV1List(reservations);

        // Then
        assertNotNull(reservationV1List);
        assertEquals(1, reservationV1List.size());
        assertEquals(reservation.id(), reservationV1List.getFirst().getId());
    }

    /**
     * Test to reservation V1 list when reservations list is null then return null.
     */
    @Test
    void testToReservationV1List_whenReservationsListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationMapper.toReservationV1List(null));
    }

    /**
     * Test to reservation V1 list when reservations list is empty then return empty list.
     */
    @Test
    void testToReservationV1List_whenReservationsListIsEmpty_thenReturnEmptyList() {
        // When
        final var reservationV1List = this.reservationMapper.toReservationV1List(List.of());

        // Then
        assertNotNull(reservationV1List);
        assertEquals(0, reservationV1List.size());
    }
}
