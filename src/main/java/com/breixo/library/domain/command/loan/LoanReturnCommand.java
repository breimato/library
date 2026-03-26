package com.breixo.library.domain.command.loan;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Loan Return Command.
 *
 * @param id         The id.
 * @param returnDate The return date.
 */
@Builder
public record LoanReturnCommand(@NotNull Integer id, @NotNull LocalDate returnDate) {
}
