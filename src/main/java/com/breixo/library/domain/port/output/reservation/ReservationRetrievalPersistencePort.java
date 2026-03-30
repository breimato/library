package com.breixo.library.domain.port.output.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Retrieval Persistence Port. */
public interface ReservationRetrievalPersistencePort {

    /**
     * Find.
     *
     * @param reservationSearchCriteriaCommand the reservation search criteria command.
     * @return the list of reservations.
     */
    List<Reservation> find(@Valid @NotNull ReservationSearchCriteriaCommand reservationSearchCriteriaCommand);
}
