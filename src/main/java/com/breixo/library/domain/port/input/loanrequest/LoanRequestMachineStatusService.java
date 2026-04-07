package com.breixo.library.domain.port.input.loanrequest;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Request Machine Status Service. */
public interface LoanRequestMachineStatusService {

    /**
     * Execute.
     *
     * @param currentStatus the current status
     * @param newStatus     the new status
     */
    void execute(@NotNull LoanRequestStatus currentStatus, @NotNull LoanRequestStatus newStatus);
}
