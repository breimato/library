package com.breixo.library.domain.port.output.reservation;

import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Mark Notified Persistence Port. */
public interface ReservationMarkNotifiedPersistencePort {

    /**
     * Mark notified by book id.
     *
     * @param bookId the book id.
     * @return the number of reservations marked as notified.
     */
    int markNotifiedByBookId(@NotNull Integer bookId);
}
