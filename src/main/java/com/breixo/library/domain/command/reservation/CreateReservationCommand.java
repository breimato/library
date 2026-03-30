package com.breixo.library.domain.command.reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Create Reservation Command.
 *
 * @param userId    The user id.
 * @param bookId    The book id.
 * @param loanId    The loan id.
 * @param expiresAt The expires at.
 * @param statusId  The status id.
 */
@Builder
public record CreateReservationCommand(@NotNull Integer userId, @NotNull Integer bookId, Integer loanId,
                                       @NotNull LocalDateTime expiresAt, @NotNull Integer statusId) {
}
