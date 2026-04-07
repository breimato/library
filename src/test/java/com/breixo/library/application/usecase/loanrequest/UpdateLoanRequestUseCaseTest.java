package com.breixo.library.application.usecase.loanrequest;

import java.util.Optional;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.event.loanrequest.LoanRequestApprovedDomainEvent;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestMachineStatusService;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestPolicyValidationService;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Update Loan Request Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateLoanRequestUseCaseTest {

    /** The update loan request use case. */
    @InjectMocks
    UpdateLoanRequestUseCaseImpl updateLoanRequestUseCase;

    /** The loan request retrieval persistence port. */
    @Mock
    LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /** The loan request update persistence port. */
    @Mock
    LoanRequestUpdatePersistencePort loanRequestUpdatePersistencePort;

    /** The loan request policy validation service. */
    @Mock
    LoanRequestPolicyValidationService loanRequestPolicyValidationService;

    /** The loan request machine status service. */
    @Mock
    LoanRequestMachineStatusService loanRequestMachineStatusService;

    /** The application event publisher. */
    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    /**
     * Test execute when valid and no status change then update and return loan request.
     */
    @Test
    void testExecute_whenValidAndNoStatusChange_thenUpdateAndReturnLoanRequest() {

        // Given
        final var updateLoanRequestCommand = Instancio.of(UpdateLoanRequestCommand.class)
                .set(org.instancio.Select.field(UpdateLoanRequestCommand::status), null)
                .create();
        final var existingLoanRequest = Instancio.of(LoanRequest.class)
                .set(org.instancio.Select.field(LoanRequest::status), LoanRequestStatus.PENDING)
                .create();
        final var updatedLoanRequest = Instancio.of(LoanRequest.class)
                .set(org.instancio.Select.field(LoanRequest::status), LoanRequestStatus.PENDING)
                .create();

        // When
        when(this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id())).thenReturn(Optional.of(existingLoanRequest));
        doNothing().when(this.loanRequestPolicyValidationService).validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                existingLoanRequest.userId(),
                updateLoanRequestCommand.status());
        when(this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand)).thenReturn(updatedLoanRequest);

        final var actualLoanRequest = this.updateLoanRequestUseCase.execute(updateLoanRequestCommand);

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).findById(updateLoanRequestCommand.id());
        verify(this.loanRequestPolicyValidationService, times(1)).validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                existingLoanRequest.userId(),
                updateLoanRequestCommand.status());
        verify(this.loanRequestMachineStatusService, times(0)).execute(existingLoanRequest.status(), existingLoanRequest.status());
        verify(this.loanRequestUpdatePersistencePort, times(1)).execute(updateLoanRequestCommand);
        verify(this.applicationEventPublisher, times(0)).publishEvent(LoanRequestApprovedDomainEvent.builder().build());
        assertEquals(updatedLoanRequest, actualLoanRequest);
    }

    /**
     * Test execute when valid and status changed to approved then update publish event and return loan request.
     */
    @Test
    void testExecute_whenValidAndStatusChangedToApproved_thenUpdatePublishEventAndReturnLoanRequest() {

        // Given
        final var updateLoanRequestCommand = Instancio.of(UpdateLoanRequestCommand.class)
                .set(org.instancio.Select.field(UpdateLoanRequestCommand::status), LoanRequestStatus.APPROVED)
                .create();
        final var existingLoanRequest = Instancio.of(LoanRequest.class)
                .set(org.instancio.Select.field(LoanRequest::status), LoanRequestStatus.PENDING)
                .create();
        final var updatedLoanRequest = Instancio.of(LoanRequest.class)
                .set(org.instancio.Select.field(LoanRequest::status), LoanRequestStatus.APPROVED)
                .create();

        final var loanRequestApprovedDomainEvent = LoanRequestApprovedDomainEvent.builder()
                .bookId(updatedLoanRequest.bookId())
                .userId(updatedLoanRequest.userId())
                .build();

        // When
        when(this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id())).thenReturn(Optional.of(existingLoanRequest));
        doNothing().when(this.loanRequestPolicyValidationService).validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                existingLoanRequest.userId(),
                updateLoanRequestCommand.status());
        doNothing().when(this.loanRequestMachineStatusService).execute(
                LoanRequestStatus.PENDING, LoanRequestStatus.APPROVED);
        when(this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand)).thenReturn(updatedLoanRequest);
        doNothing().when(this.applicationEventPublisher).publishEvent(loanRequestApprovedDomainEvent);

        final var actualLoanRequest = this.updateLoanRequestUseCase.execute(updateLoanRequestCommand);

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).findById(updateLoanRequestCommand.id());
        verify(this.loanRequestPolicyValidationService, times(1)).validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                existingLoanRequest.userId(),
                updateLoanRequestCommand.status());
        verify(this.loanRequestMachineStatusService, times(1)).execute(
                LoanRequestStatus.PENDING, LoanRequestStatus.APPROVED);
        verify(this.loanRequestUpdatePersistencePort, times(1)).execute(updateLoanRequestCommand);
        verify(this.applicationEventPublisher, times(1)).publishEvent(loanRequestApprovedDomainEvent);
        assertEquals(updatedLoanRequest, actualLoanRequest);
    }

    /**
     * Test execute when loan request already approved then update without publishing event.
     */
    @Test
    void testExecute_whenLoanRequestAlreadyApproved_thenUpdateWithoutPublishingEvent() {

        // Given
        final var updateLoanRequestCommand = Instancio.of(UpdateLoanRequestCommand.class)
                .set(org.instancio.Select.field(UpdateLoanRequestCommand::status), null)
                .create();
        final var existingLoanRequest = Instancio.of(LoanRequest.class)
                .set(org.instancio.Select.field(LoanRequest::status), LoanRequestStatus.APPROVED)
                .create();
        final var updatedLoanRequest = Instancio.of(LoanRequest.class)
                .set(org.instancio.Select.field(LoanRequest::status), LoanRequestStatus.APPROVED)
                .create();

        // When
        when(this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id())).thenReturn(Optional.of(existingLoanRequest));
        doNothing().when(this.loanRequestPolicyValidationService).validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                existingLoanRequest.userId(),
                updateLoanRequestCommand.status());
        when(this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand)).thenReturn(updatedLoanRequest);

        final var actualLoanRequest = this.updateLoanRequestUseCase.execute(updateLoanRequestCommand);

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).findById(updateLoanRequestCommand.id());
        verify(this.loanRequestPolicyValidationService, times(1)).validateTransitionAuthorization(
                updateLoanRequestCommand.requesterId(),
                existingLoanRequest.userId(),
                updateLoanRequestCommand.status());
        verify(this.loanRequestMachineStatusService, times(0)).execute(existingLoanRequest.status(), existingLoanRequest.status());
        verify(this.loanRequestUpdatePersistencePort, times(1)).execute(updateLoanRequestCommand);
        verify(this.applicationEventPublisher, times(0)).publishEvent(org.mockito.ArgumentMatchers.any());
        assertEquals(updatedLoanRequest, actualLoanRequest);
    }
}
