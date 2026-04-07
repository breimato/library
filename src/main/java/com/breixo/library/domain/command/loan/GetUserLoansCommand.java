package com.breixo.library.domain.command.loan;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Get User Loans Command.
 *
 * @param requesterId The requester id (from X-Requester-Id header).
 * @param userId      The user id.
 */
@Builder
public record GetUserLoansCommand(@NotNull Integer requesterId, @NotNull Integer userId) {

}
