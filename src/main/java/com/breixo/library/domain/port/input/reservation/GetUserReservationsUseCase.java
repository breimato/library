package com.breixo.library.domain.port.input.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.GetUserReservationsCommand;
import com.breixo.library.domain.model.reservation.Reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Get User Reservations Use Case. */
public interface GetUserReservationsUseCase {

    /**
     * Execute.
     *
     * @param getUserReservationsCommand the get user reservations command.
     * @return the list of reservations.
     */
    List<Reservation> execute(@Valid @NotNull GetUserReservationsCommand getUserReservationsCommand);
}
