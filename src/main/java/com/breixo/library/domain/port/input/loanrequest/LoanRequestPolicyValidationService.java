package com.breixo.library.domain.port.input.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;

/** The Interface Loan Request Policy Validation Service. */
public interface LoanRequestPolicyValidationService {

    /**
     * Validate creation.
     *
     * @param createLoanRequestCommand the create loan request command
     */
    void validateCreation(CreateLoanRequestCommand createLoanRequestCommand);
}
