package com.breixo.library.domain.command.loan;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Create Loan Command.
 *
 * @param userId  The user id.
 * @param bookId  The book id.
 * @param dueDate The due date.
 */
@Builder
public record CreateLoanCommand(@NotNull Integer userId, @NotNull Integer bookId, @NotNull LocalDate dueDate) {
}
