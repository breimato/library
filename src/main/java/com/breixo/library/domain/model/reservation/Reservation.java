package com.breixo.library.domain.model.reservation;

import java.time.LocalDateTime;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Reservation.
 *
 * @param id        The id.
 * @param userId    The user id.
 * @param bookId    The book id.
 * @param loanId    The loan id.
 * @param expiresAt The expires at.
 * @param status    The status.
 */
@Builder
public record Reservation(@NotNull Integer id, @NotNull Integer userId, @NotNull Integer bookId,
                          Integer loanId, @NotNull LocalDateTime expiresAt, @NotNull ReservationStatus status) {
}
