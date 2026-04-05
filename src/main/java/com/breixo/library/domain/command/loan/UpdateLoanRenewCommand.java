package com.breixo.library.domain.command.loan;

import java.time.LocalDate;

import com.breixo.library.domain.model.user.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Loan Renew Command.
 *
 * @param id                    The id.
 * @param dueDate               The new due date.
 * @param authenticatedUserId   The authenticated user id.
 * @param authenticatedUserRole The authenticated user role.
 */
@Builder
public record UpdateLoanRenewCommand(@NotNull Integer id, @NotNull LocalDate dueDate,
                                     @NotNull Integer authenticatedUserId,
                                     @NotNull UserRole authenticatedUserRole) {
}
