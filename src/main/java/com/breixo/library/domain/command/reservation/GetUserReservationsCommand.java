package com.breixo.library.domain.command.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Get User Reservations Command.
 *
 * @param requesterId The requester id (from X-Requester-Id header).
 * @param userId      The user id.
 */
@Builder
public record GetUserReservationsCommand(@NotNull Integer requesterId, @NotNull Integer userId) {

}
