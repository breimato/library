package com.breixo.library.domain.command.fine;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Create Fine Command.
 *
 * @param loanId      The loan id.
 * @param amountEuros The amount in euros.
 * @param statusId    The status id.
 */
@Builder
public record CreateFineCommand(@NotNull Integer loanId, @NotNull BigDecimal amountEuros,
                                @NotNull Integer statusId) {
}
