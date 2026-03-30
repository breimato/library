package com.breixo.library.domain.event;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;

/**
 * The Record Loan Created Domain Event.
 *
 * @param userId the user id
 * @param bookId the book id
 * @param loanId the loan id
 */
@Builder
public record LoanCreatedDomainEvent(@NotNull Integer userId, @NotNull Integer bookId, @NotNull Integer loanId) {
}
