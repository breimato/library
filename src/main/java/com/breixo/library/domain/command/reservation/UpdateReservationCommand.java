package com.breixo.library.domain.command.reservation;

import java.time.LocalDateTime;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Reservation Command.
 *
 * @param id        The id.
 * @param loanId    The loan id.
 * @param expiresAt The expires at.
 * @param status    The status.
 */
@Builder
public record UpdateReservationCommand(@NotNull Integer id, Integer loanId, LocalDateTime expiresAt,
                                       ReservationStatus status) {
}
