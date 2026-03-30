package com.breixo.library.application.event;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.event.LoanCreatedDomainEvent;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
import com.breixo.library.domain.port.output.reservation.ReservationUpdatePersistencePort;

/** The Class Reservation Fulfillment Event Listener Test. */
@ExtendWith(MockitoExtension.class)
class ReservationFulfillmentEventListenerTest {

    @InjectMocks
    private ReservationFulfillmentEventListener reservationFulfillmentEventListener;

    @Mock
    private ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    @Mock
    private ReservationUpdatePersistencePort reservationUpdatePersistencePort;

    /**
     * Test handle loan created event when user has pending reservation then complete reservation.
     */
    @Test
    void testHandleLoanCreatedEvent_whenUserHasPendingReservation_thenCompleteReservation() {

        // Given
        final var event = Instancio.create(LoanCreatedDomainEvent.class);
        final var reservation = Reservation.builder()
                .id(100)
                .userId(event.userId())
                .bookId(event.bookId())
                .build();
        final var expectedCommand = UpdateReservationCommand.builder()
                .id(reservation.id())
                .loanId(event.loanId())
                .status(ReservationStatus.FULFILLED)
                .build();

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(event.bookId())).thenReturn(List.of(reservation));

        this.reservationFulfillmentEventListener.handleLoanCreatedEvent(event);

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(event.bookId());
        verify(this.reservationRetrievalPersistencePort, times(0)).getNotifiedByBookId(event.bookId());
        verify(this.reservationUpdatePersistencePort, times(1)).execute(expectedCommand);
    }

    /**
     * Test handle loan created event when user has notified reservation then complete reservation.
     */
    @Test
    void testHandleLoanCreatedEvent_whenUserHasNotifiedReservation_thenCompleteReservation() {

        // Given
        final var event = Instancio.create(LoanCreatedDomainEvent.class);
        final var reservation = Reservation.builder()
                .id(100)
                .userId(event.userId())
                .bookId(event.bookId())
                .build();
        final var expectedCommand = UpdateReservationCommand.builder()
                .id(reservation.id())
                .loanId(event.loanId())
                .status(ReservationStatus.FULFILLED)
                .build();

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(event.bookId())).thenReturn(List.of());
        when(this.reservationRetrievalPersistencePort.getNotifiedByBookId(event.bookId())).thenReturn(List.of(reservation));

        this.reservationFulfillmentEventListener.handleLoanCreatedEvent(event);

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(event.bookId());
        verify(this.reservationRetrievalPersistencePort, times(1)).getNotifiedByBookId(event.bookId());
        verify(this.reservationUpdatePersistencePort, times(1)).execute(expectedCommand);
    }

    /**
     * Test handle loan created event when user has no reservation then do nothing.
     */
    @Test
    void testHandleLoanCreatedEvent_whenUserHasNoReservation_thenDoNothing() {

        // Given
        final var event = Instancio.create(LoanCreatedDomainEvent.class);

        // When
        when(this.reservationRetrievalPersistencePort.getPendingByBookId(event.bookId())).thenReturn(List.of());
        when(this.reservationRetrievalPersistencePort.getNotifiedByBookId(event.bookId())).thenReturn(List.of());

        this.reservationFulfillmentEventListener.handleLoanCreatedEvent(event);

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).getPendingByBookId(event.bookId());
        verify(this.reservationRetrievalPersistencePort, times(1)).getNotifiedByBookId(event.bookId());
        verify(this.reservationUpdatePersistencePort, times(0)).execute(org.mockito.ArgumentMatchers.any());
    }
}
