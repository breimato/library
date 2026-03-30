package com.breixo.library.domain.port.input.reservation;

import com.breixo.library.domain.command.reservation.UpdateReservationCommand;
import com.breixo.library.domain.model.reservation.Reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Update Reservation Use Case. */
public interface UpdateReservationUseCase {

    /**
     * Execute.
     *
     * @param updateReservationCommand the update reservation command.
     * @return the reservation.
     */
    Reservation execute(@Valid @NotNull UpdateReservationCommand updateReservationCommand);
}
