package com.breixo.library.domain.port.input.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Loan Request Policy Validation Service. */
public interface LoanRequestPolicyValidationService {

    /**
     * Validate creation.
     *
     * @param createLoanRequestCommand the create loan request command
     */
    void validateCreation(@Valid @NotNull CreateLoanRequestCommand createLoanRequestCommand);

    /**
     * Validate transition authorization.
     * APPROVED and REJECTED require at least MANAGER role.
     * Any other transition requires own resource or MANAGER.
     *
     * @param requesterId     the requester id
     * @param resourceOwnerId the loan request owner id
     * @param newStatus       the target status
     */
    void validateTransitionAuthorization(@NotNull Integer requesterId, @NotNull Integer resourceOwnerId, @NotNull LoanRequestStatus newStatus);
}
