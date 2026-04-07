package com.breixo.library.domain.command.fine;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Get User Fines Command.
 *
 * @param requesterId The requester id (from X-Requester-Id header).
 * @param userId      The user id.
 */
@Builder
public record GetUserFinesCommand(@NotNull Integer requesterId, @NotNull Integer userId) {

}
