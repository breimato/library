package com.breixo.library.domain.command.reservation;

import java.time.LocalDateTime;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import com.breixo.library.domain.model.user.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Reservation Command.
 *
 * @param id                    The id.
 * @param loanId                The loan id.
 * @param expiresAt             The expires at.
 * @param status                The status.
 * @param authenticatedUserId   The authenticated user id.
 * @param authenticatedUserRole The authenticated user role.
 */
@Builder
public record UpdateReservationCommand(@NotNull Integer id, Integer loanId, LocalDateTime expiresAt,
                                       ReservationStatus status,
                                       @NotNull Integer authenticatedUserId,
                                       @NotNull UserRole authenticatedUserRole) {
}
