package com.breixo.library.application.usecase.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.loanrequest.UpdateLoanRequestUseCase;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Update Loan Request Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateLoanRequestUseCaseImpl implements UpdateLoanRequestUseCase {

    /** The loan request retrieval persistence port. */
    private final LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /** The loan request update persistence port. */
    private final LoanRequestUpdatePersistencePort loanRequestUpdatePersistencePort;

    /** The authorization service. */
    private final AuthorizationService authorizationService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public LoanRequest execute(@Valid @NotNull final UpdateLoanRequestCommand updateLoanRequestCommand) {

        final var loanRequest = this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id());

        this.authorizationService.requireOwnResourceOrRole(
                updateLoanRequestCommand.requesterId(),
                loanRequest.userId(),
                UserRole.MANAGER);

        return this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand);
    }
}
