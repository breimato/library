package com.breixo.library.infrastructure.adapter.output.repository.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Reservation Update Repository Test. */
@ExtendWith(MockitoExtension.class)
class ReservationUpdateRepositoryTest {

    /** The reservation update repository. */
    @InjectMocks
    ReservationUpdateRepository reservationUpdateRepository;

    /** The reservation my batis mapper. */
    @Mock
    ReservationMyBatisMapper reservationMyBatisMapper;

    /** The reservation entity mapper. */
    @Mock
    ReservationEntityMapper reservationEntityMapper;

    /**
     * Test execute when command is valid then return updated reservation.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnUpdatedReservation() {

        // Given
        final var updateReservationCommand = Instancio.create(UpdateReservationCommand.class);
        final var reservationEntity = Instancio.create(ReservationEntity.class);
        final var updatedReservationEntity = Instancio.create(ReservationEntity.class);
        final var reservation = Instancio.create(Reservation.class);
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .id(updateReservationCommand.id())
                .build();

        // When
        when(this.reservationEntityMapper.toReservationEntity(updateReservationCommand)).thenReturn(reservationEntity);
        when(this.reservationMyBatisMapper.find(reservationSearchCriteriaCommand)).thenReturn(List.of(updatedReservationEntity));
        when(this.reservationEntityMapper.toReservation(updatedReservationEntity)).thenReturn(reservation);
        final var result = this.reservationUpdateRepository.execute(updateReservationCommand);

        // Then
        verify(this.reservationEntityMapper, times(1)).toReservationEntity(updateReservationCommand);
        verify(this.reservationMyBatisMapper, times(1)).update(reservationEntity);
        verify(this.reservationMyBatisMapper, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationEntityMapper, times(1)).toReservation(updatedReservationEntity);
        assertEquals(reservation, result);
    }

    /**
     * Test execute when update throws exception then throw reservation exception.
     */
    @Test
    void testExecute_whenUpdateThrowsException_thenThrowReservationException() {

        // Given
        final var updateReservationCommand = Instancio.create(UpdateReservationCommand.class);
        final var reservationEntity = Instancio.create(ReservationEntity.class);

        // When
        when(this.reservationEntityMapper.toReservationEntity(updateReservationCommand)).thenReturn(reservationEntity);
        doThrow(new RuntimeException()).when(this.reservationMyBatisMapper).update(reservationEntity);
        final var reservationException = assertThrows(ReservationException.class,
                () -> this.reservationUpdateRepository.execute(updateReservationCommand));

        // Then
        verify(this.reservationEntityMapper, times(1)).toReservationEntity(updateReservationCommand);
        verify(this.reservationMyBatisMapper, times(1)).update(reservationEntity);
        assertEquals(ExceptionMessageConstants.RESERVATION_UPDATE_ERROR_CODE_ERROR, reservationException.getCode());
        assertEquals(ExceptionMessageConstants.RESERVATION_UPDATE_ERROR_MESSAGE_ERROR, reservationException.getMessage());
    }
}
