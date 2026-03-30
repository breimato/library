package com.breixo.library.domain.port.output.reservation;

import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Deletion Persistence Port. */
public interface ReservationDeletionPersistencePort {

    /**
     * Execute.
     *
     * @param id the id
     */
    void execute(@NotNull Integer id);
}
