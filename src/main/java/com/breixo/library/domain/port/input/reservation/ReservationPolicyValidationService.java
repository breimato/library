package com.breixo.library.domain.port.input.reservation;

import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Policy Validation Service. */
public interface ReservationPolicyValidationService {

    /**
     * Check precedence.
     *
     * @param userId the user id
     * @param bookId the book id
     */
    void checkPrecedence(@NotNull Integer userId, @NotNull Integer bookId);

    /**
     * Check no active reservation.
     *
     * @param userId the user id
     * @param bookId the book id
     */
    void checkNoActiveReservation(@NotNull Integer userId, @NotNull Integer bookId);
}
