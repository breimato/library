package com.breixo.library.application.usecase.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.input.loan.UpdateLoanRenewUseCase;
import com.breixo.library.domain.port.output.loan.LoanRenewPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.input.loan.LoanStatusTransitionValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Update Loan Renew Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateLoanRenewUseCaseImpl implements UpdateLoanRenewUseCase {

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan renew persistence port. */
    private final LoanRenewPersistencePort loanRenewPersistencePort;

    /** The loan status transition validation service. */
    private final LoanStatusTransitionValidationService loanStatusTransitionValidationService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Loan execute(@Valid @NotNull final UpdateLoanRenewCommand updateLoanRenewCommand) {

        final var loan = this.getLoan(updateLoanRenewCommand);

        this.loanStatusTransitionValidationService.execute(loan.status(), LoanStatus.ACTIVE);

        if (updateLoanRenewCommand.dueDate().isBefore(loan.dueDate())) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_DUE_DATE_INVALID_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_DUE_DATE_INVALID_MESSAGE_ERROR);
        }

        return this.loanRenewPersistencePort.execute(updateLoanRenewCommand);
    }


    /**
     * Validate loan.
     *
     * @param updateLoanRenewCommand the update loan renew command
     * @return the loan
     */
    private Loan getLoan(final UpdateLoanRenewCommand updateLoanRenewCommand) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanRenewCommand.id())
                .build();

        final var loanList = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(loanList)) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR);
        }

        return loanList.getFirst();
    }
}
