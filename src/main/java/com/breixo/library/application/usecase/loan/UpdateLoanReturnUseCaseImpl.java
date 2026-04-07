package com.breixo.library.application.usecase.loan;

import java.time.LocalDate;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.event.LoanReturnedDomainEvent;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.input.loan.UpdateLoanReturnUseCase;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanUpdatePersistencePort;
import com.breixo.library.domain.port.input.fine.FineManagementService;
import com.breixo.library.domain.port.input.loan.LoanMachineStatusService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
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

    /** The loan machine status service. */
    private final LoanMachineStatusService loanMachineStatusService;

    /** The fine management service. */
    private final FineManagementService fineManagementService;

    /** The application event publisher. */
    private final ApplicationEventPublisher applicationEventPublisher;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Loan execute(@Valid @NotNull final UpdateLoanReturnCommand updateLoanReturnCommand) {

        final var loan = this.getLoan(updateLoanReturnCommand);

        if (updateLoanReturnCommand.returnDate().isAfter(LocalDate.now())) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_RETURN_DATE_INVALID_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_RETURN_DATE_INVALID_MESSAGE_ERROR);
        }

        this.loanMachineStatusService.execute(loan.status(), LoanStatus.RETURNED);

        final var updatedLoan = this.loanUpdatePersistencePort.execute(updateLoanReturnCommand);

        this.fineManagementService.execute(loan, updateLoanReturnCommand.returnDate());

        this.applicationEventPublisher.publishEvent(LoanReturnedDomainEvent.builder()
                .bookId(loan.bookId())
                .build());

        return updatedLoan;
    }

    /**
     * Gets the loan.
     *
     * @param updateLoanReturnCommand the update loan return command
     * @return the loan
     */
    private Loan getLoan(final UpdateLoanReturnCommand updateLoanReturnCommand) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanReturnCommand.id())
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
