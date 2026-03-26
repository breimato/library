package com.breixo.library.application.usecase.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.ReturnLoanCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.LoanReturnUseCase;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/** The Class Loan Return Use Case Impl. */
@Component
@RequiredArgsConstructor
public class LoanReturnUseCaseImpl implements LoanReturnUseCase {

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan update persistence port. */
    private final LoanUpdatePersistencePort loanUpdatePersistencePort;

    /** {@inheritDoc} */
    @Override
    public Loan execute(@Valid @NotNull final ReturnLoanCommand returnLoanCommand) {
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(returnLoanCommand.id())
                .build();
        final var loanExists = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand).isPresent();

        if (BooleanUtils.isFalse(loanExists)) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR);
        }

        return this.loanUpdatePersistencePort.execute(returnLoanCommand);
    }
}
