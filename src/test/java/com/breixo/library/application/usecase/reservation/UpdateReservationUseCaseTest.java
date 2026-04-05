package com.breixo.library.application.usecase.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.domain.port.output.reservation.ReservationUpdatePersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Update Reservation Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateReservationUseCaseTest {

    /** The update reservation use case. */
    @InjectMocks
    UpdateReservationUseCaseImpl updateReservationUseCase;

    /** The reservation retrieval persistence port. */
    @Mock
    ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /** The reservation update persistence port. */
    @Mock
    ReservationUpdatePersistencePort reservationUpdatePersistencePort;

    /**
     * Test execute when reservation exists then update and return reservation.
     */
    @Test
    void testExecute_whenReservationExists_thenUpdateAndReturnReservation() {

        // Given
        final var updateReservationCommand = Instancio.of(UpdateReservationCommand.class)
            .set(org.instancio.Select.field("authenticatedUserRole"), com.breixo.library.domain.model.user.enums.UserRole.MANAGER)
            .create();
        final var existingReservation = Instancio.create(Reservation.class);
        final var updatedReservation = Instancio.create(Reservation.class);
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .id(updateReservationCommand.id())
                .build();

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand))
                .thenReturn(List.of(existingReservation));
        when(this.reservationUpdatePersistencePort.execute(updateReservationCommand)).thenReturn(updatedReservation);
        final var result = this.updateReservationUseCase.execute(updateReservationCommand);

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationUpdatePersistencePort, times(1)).execute(updateReservationCommand);
        assertEquals(updatedReservation, result);
    }

    /**
     * Test execute when reservation not found then throw reservation exception.
     */
    @Test
    void testExecute_whenReservationNotFound_thenThrowReservationException() {

        // Given
        final var updateReservationCommand = Instancio.create(UpdateReservationCommand.class);
        final var reservationSearchCriteriaCommand = ReservationSearchCriteriaCommand.builder()
                .id(updateReservationCommand.id())
                .build();

        // When
        when(this.reservationRetrievalPersistencePort.find(reservationSearchCriteriaCommand)).thenReturn(List.of());
        final var reservationException = assertThrows(ReservationException.class,
                () -> this.updateReservationUseCase.execute(updateReservationCommand));

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).find(reservationSearchCriteriaCommand);
        verify(this.reservationUpdatePersistencePort, times(0)).execute(updateReservationCommand);
        assertEquals(ExceptionMessageConstants.RESERVATION_NOT_FOUND_MESSAGE_ERROR, reservationException.getMessage());
    }
}
