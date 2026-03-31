package com.breixo.library.domain.command.loan;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Loan Renew Command.
 *
 * @param id      The id.
 * @param dueDate The new due date.
 */
@Builder
public record UpdateLoanRenewCommand(@NotNull Integer id, @NotNull LocalDate dueDate) {
}
