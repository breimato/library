package com.breixo.library.domain.event.loanrequest;

import lombok.Builder;

/**
 * The Record Loan Request Approved Domain Event.
 *
 * @param bookId The book id.
 * @param userId The user id.
 */
@Builder
public record LoanRequestApprovedDomainEvent(Integer bookId, Integer userId) {
}
