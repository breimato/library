package com.breixo.library.domain.event;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Loan Returned Domain Event.
 *
 * @param bookId the book id
 */
@Builder
public record LoanReturnedDomainEvent(@NotNull Integer bookId) {
}
