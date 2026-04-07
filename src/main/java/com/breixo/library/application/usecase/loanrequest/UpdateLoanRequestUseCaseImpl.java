package com.breixo.library.application.usecase.loanrequest;

import java.util.Objects;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.event.loanrequest.LoanRequestApprovedDomainEvent;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestMachineStatusService;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestPolicyValidationService;
import com.breixo.library.domain.port.input.loanrequest.UpdateLoanRequestUseCase;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Update Loan Request Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateLoanRequestUseCaseImpl implements UpdateLoanRequestUseCase {

    /**
     * The loan request retrieval persistence port.
     */
    private final LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /**
     * The loan request update persistence port.
     */
    private final LoanRequestUpdatePersistencePort loanRequestUpdatePersistencePort;

    /**
     * The loan request policy validation service.
     */
    private final LoanRequestPolicyValidationService loanRequestPolicyValidationService;

    /**
     * The loan request machine status service.
     */
    private final LoanRequestMachineStatusService loanRequestMachineStatusService;

    /**
     * The application event publisher.
     */
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LoanRequest execute(@Valid @NotNull final UpdateLoanRequestCommand updateLoanRequestCommand) {

        final var loanRequest = this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id());

        this.loanRequestPolicyValidationService.validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                loanRequest.userId(),
                updateLoanRequestCommand.status());

        if (Objects.nonNull(updateLoanRequestCommand.status())) {
            this.loanRequestMachineStatusService.execute(
                    loanRequest.status(),
                    updateLoanRequestCommand.status());
        }

        final var updatedLoanRequest = this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand);

        if (BooleanUtils.isFalse(loanRequest.status().equals(LoanRequestStatus.APPROVED)) &&
                updatedLoanRequest.status().equals(LoanRequestStatus.APPROVED)) {

            this.applicationEventPublisher.publishEvent(
                    LoanRequestApprovedDomainEvent.builder()
                            .bookId(updatedLoanRequest.bookId())
                            .userId(updatedLoanRequest.userId())
                            .build());
        }

        return updatedLoanRequest;
    }
}
