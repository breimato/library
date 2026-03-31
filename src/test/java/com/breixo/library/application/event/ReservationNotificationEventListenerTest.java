package com.breixo.library.application.event;

import com.breixo.library.domain.event.LoanReturnedDomainEvent;
import com.breixo.library.domain.port.output.reservation.ReservationMarkNotifiedPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Reservation Notification Event Listener Test. */
@ExtendWith(MockitoExtension.class)
class ReservationNotificationEventListenerTest {

    /** The reservation notification event listener. */
    @InjectMocks
    ReservationNotificationEventListener reservationNotificationEventListener;

    /** The reservation mark notified persistence port. */
    @Mock
    ReservationMarkNotifiedPersistencePort reservationMarkNotifiedPersistencePort;

    /**
     * Test handle loan returned event when book has pending reservations then mark as notified.
     */
    @Test
    void testHandleLoanReturnedEvent_whenBookHasPendingReservations_thenMarkAsNotified() {

        // Given
        final var event = Instancio.create(LoanReturnedDomainEvent.class);
        final var notifiedCount = Instancio.create(Integer.class);

        // When
        when(this.reservationMarkNotifiedPersistencePort.markNotifiedByBookId(event.bookId()))
                .thenReturn(notifiedCount);

        this.reservationNotificationEventListener.handleLoanReturnedEvent(event);

        // Then
        verify(this.reservationMarkNotifiedPersistencePort, times(1)).markNotifiedByBookId(event.bookId());
    }

    /**
     * Test handle loan returned event when book has no pending reservations then do nothing.
     */
    @Test
    void testHandleLoanReturnedEvent_whenBookHasNoPendingReservations_thenDoNothing() {

        // Given
        final var event = Instancio.create(LoanReturnedDomainEvent.class);

        // When
        when(this.reservationMarkNotifiedPersistencePort.markNotifiedByBookId(event.bookId())).thenReturn(0);

        this.reservationNotificationEventListener.handleLoanReturnedEvent(event);

        // Then
        verify(this.reservationMarkNotifiedPersistencePort, times(1)).markNotifiedByBookId(event.bookId());
    }
}
