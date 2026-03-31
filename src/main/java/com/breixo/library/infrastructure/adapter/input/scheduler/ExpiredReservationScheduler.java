package com.breixo.library.infrastructure.adapter.input.scheduler;

import com.breixo.library.domain.port.output.reservation.ReservationMarkExpiredPersistencePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** The Class Expired Reservation Scheduler. */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExpiredReservationScheduler {

    /** The Reservation Mark Expired Persistence Port. */
    private final ReservationMarkExpiredPersistencePort reservationMarkExpiredPersistencePort;

    /**
     * Mark expired reservations.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void markExpiredReservations() {

        final var updatedCount = this.reservationMarkExpiredPersistencePort.markExpired();

        log.info("Expired reservations marked: {}", updatedCount);
    }
}
