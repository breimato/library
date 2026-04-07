package com.breixo.library.domain.port.output.reservation;

import java.util.List;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.domain.model.reservation.Reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Retrieval Persistence Port. */
public interface ReservationRetrievalPersistencePort {

    /**
     * Gets pending by book id.
     *
     * @param bookId the book id.
     * @return the list of reservations.
     */
    List<Reservation> getPendingByBookId(@NotNull Integer bookId);

    /**
     * Gets notified by book id.
     *
     * @param bookId the book id.
     * @return the list of reservations.
     */
    List<Reservation> getNotifiedByBookId(@NotNull Integer bookId);

    /**
     * Gets active by book id.
     *
     * @param bookId the book id.
     * @return the list of active reservations (pending and notified).
     */
    List<Reservation> getActiveByBookId(@NotNull Integer bookId);

    /**
     * Find.
     *
     * @param reservationSearchCriteriaCommand the reservation search criteria command.
     * @return the list of reservations.
     */
    List<Reservation> find(@Valid @NotNull ReservationSearchCriteriaCommand reservationSearchCriteriaCommand);

    /**
     * Find by user id.
     *
     * @param userId the user id.
     * @return the list of reservations.
     */
    List<Reservation> findByUserId(@NotNull Integer userId);
}
