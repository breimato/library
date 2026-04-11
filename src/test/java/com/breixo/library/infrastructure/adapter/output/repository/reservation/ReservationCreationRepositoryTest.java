package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.output.entities.ReservationEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.reservation.ReservationEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.ReservationMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Reservation Creation Repository Test. */
@ExtendWith(MockitoExtension.class)
class ReservationCreationRepositoryTest {

    /** The Reservation Creation Repository. */
    @InjectMocks
    ReservationCreationRepository reservationCreationRepository;

    /** The Reservation My Batis Mapper. */
    @Mock
    ReservationMyBatisMapper reservationMyBatisMapper;

    /** The Reservation Entity Mapper. */
    @Mock
    ReservationEntityMapper reservationEntityMapper;

    /**
     * Test execute when command is valid then return created reservation.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnCreatedReservation() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var reservationEntity = Instancio.create(ReservationEntity.class);
        final var createdReservationEntity = Instancio.create(ReservationEntity.class);
        final var reservation = Instancio.create(Reservation.class);
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .id(reservationEntity.getId())
                .build();

        // When
        when(this.reservationEntityMapper.toReservationEntity(createReservationCommand)).thenReturn(reservationEntity);
        when(this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand)).thenReturn(List.of(createdReservationEntity));
        when(this.reservationEntityMapper.toReservation(createdReservationEntity)).thenReturn(reservation);
        final var result = this.reservationCreationRepository.execute(createReservationCommand);

        // Then
        verify(this.reservationEntityMapper, times(1)).toReservationEntity(createReservationCommand);
        verify(this.reservationMyBatisMapper, times(1)).insert(reservationEntity);
        verify(this.reservationMyBatisMapper, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationEntityMapper, times(1)).toReservation(createdReservationEntity);
        assertEquals(reservation, result);
    }
}
