package com.breixo.library.domain.port.output.reservation;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.model.reservation.Reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Creation Persistence Port. */
public interface ReservationCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createReservationCommand the create reservation command.
     * @return the created reservation.
     */
    Reservation execute(@Valid @NotNull CreateReservationCommand createReservationCommand);
}
