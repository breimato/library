package com.breixo.library.domain.model.loanrequest;

import java.time.LocalDate;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Loan Request.
 *
 * @param id               The id.
 * @param userId           The user id.
 * @param bookId           The book id.
 * @param requestDate      The request date.
 * @param approvalDate     The approval date.
 * @param status           The status.
 * @param rejectionReason  The rejection reason.
 */
@Builder
public record LoanRequest(@NotNull Integer id, @NotNull Integer userId, @NotNull Integer bookId,
                          @NotNull LocalDate requestDate, LocalDate approvalDate,
                          @NotNull LoanRequestStatus status, String rejectionReason) {
}
