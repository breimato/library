package com.breixo.library.domain.port.input.fine;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/** The Interface Fine Calculation Service. */
public interface FineCalculationService {

    /**
     * Execute.
     *
     * @param dueDate    the due date
     * @param returnDate the return date
     * @return the fine amount
     */
    BigDecimal execute(@NotNull LocalDate dueDate, @NotNull LocalDate returnDate);
}
