package com.breixo.library.domain.command.loan;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Loan Return Command.
 *
 * @param id         The id.
 * @param returnDate The return date.
 */
@Builder
public record UpdateLoanReturnCommand(@NotNull Integer id, @NotNull LocalDate returnDate) {
}
