package com.breixo.library.domain.port.output.reservation;

/** The Interface Reservation Mark Expired Persistence Port. */
public interface ReservationMarkExpiredPersistencePort {

    /**
     * Mark expired.
     *
     * @return the number of reservations marked as expired.
     */
    int markExpired();
}
