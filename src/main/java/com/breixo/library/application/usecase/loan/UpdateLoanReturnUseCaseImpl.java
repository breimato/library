package com.breixo.library.application.usecase.loan;

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

    /** The fine management service. */
    private final FineManagementService fineManagementService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Loan execute(@Valid @NotNull final UpdateLoanReturnCommand updateLoanReturnCommand) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(updateLoanReturnCommand.id()).build();
        final var loans = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(loans)) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR);
        }

        if (LoanStatus.RETURNED.equals(loans.getFirst().status())) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR);
        }

        final var updatedLoan = this.loanUpdatePersistencePort.execute(updateLoanReturnCommand);

        this.fineManagementService.execute(loans.getFirst(), updateLoanReturnCommand.returnDate());

        return updatedLoan;
    }

}
