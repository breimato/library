package com.breixo.library.domain.port.input.loan;

import com.breixo.library.domain.model.loan.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Status Transition Validation Service. */
public interface LoanStatusTransitionValidationService {

    /**
     * Execute.
     *
     * @param currentStatus the current status
     * @param newStatus     the new status
     */
    void execute(@NotNull LoanStatus currentStatus, @NotNull LoanStatus newStatus);
}
