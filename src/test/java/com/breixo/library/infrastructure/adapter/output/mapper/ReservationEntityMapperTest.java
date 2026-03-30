package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;
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

/** The Class Reservation Entity Mapper Test. */
@ExtendWith(MockitoExtension.class)
class ReservationEntityMapperTest {

    /** The reservation entity mapper. */
    @InjectMocks
    ReservationEntityMapperImpl reservationEntityMapper;

    /** The reservation status mapper. */
    @Mock
    ReservationStatusMapper reservationStatusMapper;

    /**
     * Test to reservation when reservation entity is valid then return mapped reservation.
     */
    @Test
    void testToReservation_whenReservationEntityIsValid_thenReturnMappedReservation() {

        // Given
        final var reservationEntity = Instancio.create(ReservationEntity.class);
        final var reservationStatus = Instancio.create(ReservationStatus.class);

        // When
        when(this.reservationStatusMapper.toReservationStatus(reservationEntity.getStatusId())).thenReturn(reservationStatus);
        final var reservation = this.reservationEntityMapper.toReservation(reservationEntity);

        // Then
        verify(this.reservationStatusMapper, times(1)).toReservationStatus(reservationEntity.getStatusId());
        assertNotNull(reservation);
        assertEquals(reservationEntity.getId(), reservation.id());
        assertEquals(reservationEntity.getUserId(), reservation.userId());
        assertEquals(reservationEntity.getBookId(), reservation.bookId());
        assertEquals(reservationEntity.getLoanId(), reservation.loanId());
        assertEquals(reservationEntity.getExpiresAt(), reservation.expiresAt());
        assertEquals(reservationStatus, reservation.status());
    }

    /**
     * Test to reservation when reservation entity is null then return null.
     */
    @Test
    void testToReservation_whenReservationEntityIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationEntityMapper.toReservation(null));
    }

    /**
     * Test to reservation list when reservation entities are valid then return mapped reservation list.
     */
    @Test
    void testToReservationList_whenReservationEntitiesAreValid_thenReturnMappedReservationList() {

        // Given
        final var reservationEntity = Instancio.create(ReservationEntity.class);
        final var reservationEntities = List.of(reservationEntity);
        final var reservationStatus = Instancio.create(ReservationStatus.class);

        // When
        when(this.reservationStatusMapper.toReservationStatus(reservationEntity.getStatusId())).thenReturn(reservationStatus);
        final var reservations = this.reservationEntityMapper.toReservationList(reservationEntities);

        // Then
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(reservationEntity.getId(), reservations.getFirst().id());
    }

    /**
     * Test to reservation list when reservation entities list is null then return null.
     */
    @Test
    void testToReservationList_whenReservationEntitiesListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationEntityMapper.toReservationList(null));
    }

    /**
     * Test to reservation list when reservation entities list is empty then return empty list.
     */
    @Test
    void testToReservationList_whenReservationEntitiesListIsEmpty_thenReturnEmptyList() {
        // When
        final var reservations = this.reservationEntityMapper.toReservationList(List.of());

        // Then
        assertNotNull(reservations);
        assertEquals(0, reservations.size());
    }
}
