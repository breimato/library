package com.breixo.library.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/** The Class Fine Calculation Service Impl. */
@Component
public class FineCalculationServiceImpl implements FineCalculationService {

    /** The Constant DAILY_FINE_RATE. */
    private static final BigDecimal DAILY_FINE_RATE = BigDecimal.valueOf(0.5);

    /** {@inheritDoc} */
    @Override
    public BigDecimal execute(@NotNull final LocalDate dueDate, @NotNull final LocalDate returnDate) {

        if (BooleanUtils.isFalse(returnDate.isAfter(dueDate))) {
            return BigDecimal.ZERO;
        }

        final var overdueDays = BigDecimal.valueOf(ChronoUnit.DAYS.between(dueDate, returnDate));
        return DAILY_FINE_RATE.multiply(overdueDays);
    }
}
