package com.breixo.library.application.usecase.loanrequest;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.exception.AuthorizationException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.domain.port.input.loanrequest.UpdateLoanRequestUseCase;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/** The Class Update Loan Request Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateLoanRequestUseCaseImpl implements UpdateLoanRequestUseCase {

    /** The loan request retrieval persistence port. */
    private final LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /** The loan request update persistence port. */
    private final LoanRequestUpdatePersistencePort loanRequestUpdatePersistencePort;

    /** The create loan use case. */
    private final CreateLoanUseCase createLoanUseCase;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public LoanRequest execute(@Valid @NotNull final UpdateLoanRequestCommand updateLoanRequestCommand) {

        final var loanRequest = this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id());

        this.validateAuthorization(updateLoanRequestCommand, loanRequest);

        final var updatedLoanRequest = this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand);

        if (LoanRequestStatus.APPROVED.equals(updateLoanRequestCommand.status())) {
            this.createLoanFromApprovedRequest(loanRequest);
        }

        return updatedLoanRequest;
    }

    /**
     * Validate that the authenticated user is authorized to perform the update.
     *
     * @param command     the update command
     * @param loanRequest the existing loan request
     */
    private void validateAuthorization(final UpdateLoanRequestCommand command, final LoanRequest loanRequest) {

        if (UserRole.NORMAL.equals(command.authenticatedUserRole())) {

            if (!loanRequest.userId().equals(command.authenticatedUserId())) {
                throw new AuthorizationException(
                        ExceptionMessageConstants.AUTH_RESOURCE_OWNERSHIP_CODE_ERROR,
                        ExceptionMessageConstants.AUTH_RESOURCE_OWNERSHIP_MESSAGE_ERROR);
            }

            if (!LoanRequestStatus.CANCELLED.equals(command.status())) {
                throw new AuthorizationException(
                        ExceptionMessageConstants.AUTH_INSUFFICIENT_ROLE_CODE_ERROR,
                        ExceptionMessageConstants.AUTH_INSUFFICIENT_ROLE_MESSAGE_ERROR);
            }
        }
    }

    /**
     * Create a loan automatically when a loan request is approved.
     *
     * @param loanRequest the approved loan request
     */
    private void createLoanFromApprovedRequest(final LoanRequest loanRequest) {

        final var createLoanCommand = CreateLoanCommand.builder()
                .userId(loanRequest.userId())
                .bookId(loanRequest.bookId())
                .dueDate(LocalDate.now().plusDays(14))
                .build();

        this.createLoanUseCase.execute(createLoanCommand);
    }
}
