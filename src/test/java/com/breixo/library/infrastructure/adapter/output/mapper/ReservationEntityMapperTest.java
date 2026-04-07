package com.breixo.library.infrastructure.adapter.output.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
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

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to reservation when reservation entity is valid then return mapped reservation.
     */
    @Test
    void testToReservation_whenReservationEntityIsValid_thenReturnMappedReservation() {

        // Given
        final var reservationEntity = Instancio.create(ReservationEntity.class);
        final var reservationStatus = Instancio.create(ReservationStatus.class);
        final var expiresAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.reservationStatusMapper.toReservationStatus(reservationEntity.getStatusId())).thenReturn(reservationStatus);
        when(this.dateMapper.toLocalDateTime(reservationEntity.getExpiresAt())).thenReturn(expiresAt);
        final var reservation = this.reservationEntityMapper.toReservation(reservationEntity);

        // Then
        verify(this.reservationStatusMapper, times(1)).toReservationStatus(reservationEntity.getStatusId());
        verify(this.dateMapper, times(1)).toLocalDateTime(reservationEntity.getExpiresAt());
        assertNotNull(reservation);
        assertEquals(reservationEntity.getId(), reservation.id());
        assertEquals(reservationEntity.getUserId(), reservation.userId());
        assertEquals(reservationEntity.getBookId(), reservation.bookId());
        assertEquals(reservationEntity.getLoanId(), reservation.loanId());
        assertEquals(expiresAt, reservation.expiresAt());
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
        final var expiresAt = Instancio.create(LocalDateTime.class);

        // When
        when(this.reservationStatusMapper.toReservationStatus(reservationEntity.getStatusId())).thenReturn(reservationStatus);
        when(this.dateMapper.toLocalDateTime(reservationEntity.getExpiresAt())).thenReturn(expiresAt);
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

    /**
     * Test to reservation entity when create reservation command is valid then return mapped entity.
     */
    @Test
    void testToReservationEntity_whenCreateReservationCommandIsValid_thenReturnMappedEntity() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var offsetDateTime = Instancio.create(OffsetDateTime.class);

        // When
        when(this.dateMapper.toOffsetDateTime(createReservationCommand.expiresAt())).thenReturn(offsetDateTime);
        final var reservationEntity = this.reservationEntityMapper.toReservationEntity(createReservationCommand);

        // Then
        verify(this.dateMapper, times(1)).toOffsetDateTime(createReservationCommand.expiresAt());
        assertNotNull(reservationEntity);
        assertEquals(createReservationCommand.userId(), reservationEntity.getUserId());
        assertEquals(createReservationCommand.bookId(), reservationEntity.getBookId());
        assertEquals(createReservationCommand.loanId(), reservationEntity.getLoanId());
        assertEquals(offsetDateTime, reservationEntity.getExpiresAt());
        assertEquals(createReservationCommand.statusId(), reservationEntity.getStatusId());
    }

    /**
     * Test to reservation entity when update reservation command is valid then return mapped entity.
     */
    @Test
    void testToReservationEntity_whenUpdateReservationCommandIsValid_thenReturnMappedEntity() {

        // Given
        final var updateReservationCommand = Instancio.create(UpdateReservationCommand.class);
        final var statusId = Instancio.create(Integer.class);
        final var offsetDateTime = Instancio.create(OffsetDateTime.class);

        // When
        when(this.reservationStatusMapper.toStatusId(updateReservationCommand.status())).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(updateReservationCommand.expiresAt())).thenReturn(offsetDateTime);
        final var reservationEntity = this.reservationEntityMapper.toReservationEntity(updateReservationCommand);

        // Then
        verify(this.reservationStatusMapper, times(1)).toStatusId(updateReservationCommand.status());
        verify(this.dateMapper, times(1)).toOffsetDateTime(updateReservationCommand.expiresAt());
        assertNotNull(reservationEntity);
        assertEquals(updateReservationCommand.id(), reservationEntity.getId());
        assertEquals(updateReservationCommand.loanId(), reservationEntity.getLoanId());
        assertEquals(offsetDateTime, reservationEntity.getExpiresAt());
        assertEquals(statusId, reservationEntity.getStatusId());
    }
}
