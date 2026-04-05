package com.breixo.library.domain.command.loanrequest;

import com.breixo.library.domain.model.user.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Create Loan Request Command.
 *
 * @param userId                The user id.
 * @param bookId                The book id.
 * @param authenticatedUserId   The authenticated user id.
 * @param authenticatedUserRole The authenticated user role.
 */
@Builder
public record CreateLoanRequestCommand(@NotNull Integer userId, @NotNull Integer bookId,
                                       @NotNull Integer authenticatedUserId,
                                       @NotNull UserRole authenticatedUserRole) {

}
