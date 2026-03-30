package com.breixo.library.domain.service;

import jakarta.validation.constraints.NotNull;

/** The Interface Reservation Policy Validation Service. */
public interface ReservationPolicyValidationService {

    /**
     * Check reservation precedence.
     *
     * @param userId the user id
     * @param bookId the book id
     */
    void checkReservationPrecedence(@NotNull Integer userId, @NotNull Integer bookId);
}
