package com.breixo.library.domain.command.loanrequest;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Create Loan Request Command.
 *
 * @param requesterId The requester id (from X-Requester-Id header).
 * @param userId The user id.
 * @param bookId The book id.
 */
@Builder
public record CreateLoanRequestCommand(@NotNull Integer requesterId, @NotNull Integer userId, @NotNull Integer bookId) {

}
