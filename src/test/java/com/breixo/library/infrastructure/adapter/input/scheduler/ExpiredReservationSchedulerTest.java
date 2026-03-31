package com.breixo.library.infrastructure.adapter.input.scheduler;

import com.breixo.library.domain.port.output.reservation.ReservationMarkExpiredPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Expired Reservation Scheduler Test. */
@ExtendWith(MockitoExtension.class)
class ExpiredReservationSchedulerTest {

    /** The expired reservation scheduler. */
    @InjectMocks
    ExpiredReservationScheduler expiredReservationScheduler;

    /** The reservation mark expired persistence port. */
    @Mock
    ReservationMarkExpiredPersistencePort reservationMarkExpiredPersistencePort;

    /**
     * Test mark expired reservations when called then delegates to port.
     */
    @Test
    void testMarkExpiredReservations_whenCalled_thenDelegatesToPort() {

        // Given
        final var updatedCount = Instancio.create(Integer.class);

        // When
        when(this.reservationMarkExpiredPersistencePort.markExpired()).thenReturn(updatedCount);
        this.expiredReservationScheduler.markExpiredReservations();

        // Then
        verify(this.reservationMarkExpiredPersistencePort, times(1)).markExpired();
    }
}
