package com.breixo.library.domain.model.fine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.breixo.library.domain.model.fine.enums.FineStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Fine.
 *
 * @param id          The id.
 * @param loanId      The loan id.
 * @param amountEuros The amount in euros.
 * @param status      The status.
 * @param paidAt      The paid at.
 */
@Builder
public record Fine(@NotNull Integer id, @NotNull Integer loanId, @NotNull BigDecimal amountEuros,
                   @NotNull FineStatus status, LocalDateTime paidAt) {
}
