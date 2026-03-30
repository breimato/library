package com.breixo.library.domain.command.fine;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.breixo.library.domain.model.fine.enums.FineStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update Fine Command.
 *
 * @param id          The id.
 * @param amountEuros The amount in euros.
 * @param status      The status.
 * @param paidAt      The paid at.
 */
@Builder
public record UpdateFineCommand(@NotNull Integer id, BigDecimal amountEuros, FineStatus status,
                                LocalDateTime paidAt) {
}
