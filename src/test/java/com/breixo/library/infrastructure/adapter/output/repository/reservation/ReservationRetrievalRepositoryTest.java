package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.ReservationEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Reservation Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class ReservationRetrievalRepositoryTest {

    /** The reservation retrieval repository. */
    @InjectMocks
    ReservationRetrievalRepository reservationRetrievalRepository;

    /** The reservation my batis mapper. */
    @Mock
    ReservationMyBatisMapper reservationMyBatisMapper;

    /** The reservation entity mapper. */
    @Mock
    ReservationEntityMapper reservationEntityMapper;

    /**
     * Test find when reservations exist then return reservations.
     */
    @Test
    void testFind_whenReservationsExist_thenReturnReservations() {

        // Given
        final var reservationSearchCriteriaCommand = Instancio.create(ReservationSearchCriteriaCommand.class);
        final var reservationEntities = Instancio.createList(ReservationEntity.class);
        final var reservations = Instancio.createList(Reservation.class);

        // When
        when(this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand)).thenReturn(reservationEntities);
        when(this.reservationEntityMapper.toReservationList(reservationEntities)).thenReturn(reservations);
        final var result = this.reservationRetrievalRepository.find(reservationSearchCriteriaCommand);

        // Then
        verify(this.reservationMyBatisMapper, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationEntityMapper, times(1)).toReservationList(reservationEntities);
        assertEquals(reservations, result);
    }

    /**
     * Test find when no reservations match criteria then return empty list.
     */
    @Test
    void testFind_whenNoReservationsMatchCriteria_thenReturnEmptyList() {

        // Given
        final var reservationSearchCriteriaCommand = Instancio.create(ReservationSearchCriteriaCommand.class);
        final var reservations = List.<Reservation>of();

        // When
        when(this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand)).thenReturn(List.of());
        when(this.reservationEntityMapper.toReservationList(List.of())).thenReturn(reservations);
        final var result = this.reservationRetrievalRepository.find(reservationSearchCriteriaCommand);

        // Then
        verify(this.reservationMyBatisMapper, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationEntityMapper, times(1)).toReservationList(List.of());
        assertTrue(result.isEmpty());
    }
}
