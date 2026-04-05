package com.breixo.library.domain.command.loanrequest;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.domain.model.user.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Loan Request Command.
 *
 * @param id                    The id.
 * @param status                The status.
 * @param rejectionReason       The rejection reason.
 * @param authenticatedUserId   The authenticated user id.
 * @param authenticatedUserRole The authenticated user role.
 */
@Builder
public record UpdateLoanRequestCommand(@NotNull Integer id, LoanRequestStatus status, String rejectionReason,
                                       @NotNull Integer authenticatedUserId,
                                       @NotNull UserRole authenticatedUserRole) {

}
