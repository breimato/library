package com.breixo.library.application.usecase.loan;

import java.time.LocalDate;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.input.loan.UpdateLoanReturnUseCase;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanUpdatePersistencePort;
import com.breixo.library.domain.service.FineManagementService;
import com.breixo.library.domain.service.LoanStatusTransitionValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Update Loan Return Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateLoanReturnUseCaseImpl implements UpdateLoanReturnUseCase {

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan update persistence port. */
    private final LoanUpdatePersistencePort loanUpdatePersistencePort;

    /** The loan status transition validation service. */
    private final LoanStatusTransitionValidationService loanStatusTransitionValidationService;

    /** The fine management service. */
    private final FineManagementService fineManagementService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Loan execute(@Valid @NotNull final UpdateLoanReturnCommand updateLoanReturnCommand) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(updateLoanReturnCommand.id())
                .build();
        final var loans = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(loans)) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR);
        }

        if (updateLoanReturnCommand.returnDate().isAfter(LocalDate.now())) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_RETURN_DATE_INVALID_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_RETURN_DATE_INVALID_MESSAGE_ERROR);
        }

        this.loanStatusTransitionValidationService.execute(loans.getFirst().status(), LoanStatus.RETURNED);

        final var updatedLoan = this.loanUpdatePersistencePort.execute(updateLoanReturnCommand);

        this.fineManagementService.execute(loans.getFirst(), updateLoanReturnCommand.returnDate());

        return updatedLoan;
    }

}
