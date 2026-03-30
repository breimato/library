package com.breixo.library.domain.service;

import java.time.LocalDate;

import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Fine Management Service. */
public interface FineManagementService {

    /**
     * Execute.
     *
     * @param loan       the loan.
     * @param returnDate the return date.
     */
    void execute(@Valid @NotNull Loan loan, @NotNull LocalDate returnDate);
}
