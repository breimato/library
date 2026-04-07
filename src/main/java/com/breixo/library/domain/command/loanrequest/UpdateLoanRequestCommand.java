package com.breixo.library.domain.command.loanrequest;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Loan Request Command.
 *
 * @param requesterId     The requester id (from X-Requester-Id header).
 * @param id              The id.
 * @param status          The status.
 * @param rejectionReason The rejection reason.
 */
@Builder
public record UpdateLoanRequestCommand(@NotNull Integer requesterId, @NotNull Integer id, LoanRequestStatus status, String rejectionReason) {

}
