package com.breixo.library.application.usecase.loan;

import java.time.LocalDate;
import java.util.List;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.output.loan.LoanRenewPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.service.LoanStatusTransitionValidationService;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Update Loan Renew Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateLoanRenewUseCaseTest {

    /** The update loan renew use case. */
    @InjectMocks
    UpdateLoanRenewUseCaseImpl updateLoanRenewUseCase;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan renew persistence port. */
    @Mock
    LoanRenewPersistencePort loanRenewPersistencePort;

    /** The loan status transition validation service. */
    @Mock
    LoanStatusTransitionValidationService loanStatusTransitionValidationService;

    /**
     * Test execute when loan not found then throw loan exception.
     */
    @Test
    void testExecute_whenLoanNotFound_thenThrowLoanException() {

        // Given
        final var updateLoanRenewCommand = Instancio.create(UpdateLoanRenewCommand.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanRenewCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(List.of());
        final var loanException = assertThrows(LoanException.class,
                () -> this.updateLoanRenewUseCase.execute(updateLoanRenewCommand));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanRenewPersistencePort, times(0)).execute(updateLoanRenewCommand);
        assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test execute when loan already returned then throw loan exception.
     */
    @Test
    void testExecute_whenLoanAlreadyReturned_thenThrowLoanException() {

        // Given
        final var existingDueDate = LocalDate.now().plusDays(5);
        final var updateLoanRenewCommand = UpdateLoanRenewCommand.builder()
                .id(Instancio.create(Integer.class))
                .dueDate(existingDueDate.plusDays(7))
                .build();
        final var returnedLoan = Loan.builder()
                .id(updateLoanRenewCommand.id())
                .userId(Instancio.create(Integer.class))
                .bookId(Instancio.create(Integer.class))
                .dueDate(existingDueDate)
                .status(LoanStatus.RETURNED)
                .build();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanRenewCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand))
                .thenReturn(List.of(returnedLoan));
        doThrow(new LoanException(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR,
                ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR))
                .when(this.loanStatusTransitionValidationService)
                .execute(returnedLoan.status(), LoanStatus.ACTIVE);

        final var loanException = assertThrows(LoanException.class,
                () -> this.updateLoanRenewUseCase.execute(updateLoanRenewCommand));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanStatusTransitionValidationService, times(1)).execute(returnedLoan.status(), LoanStatus.ACTIVE);
        verify(this.loanRenewPersistencePort, times(0)).execute(updateLoanRenewCommand);
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test execute when due date is not after current due date then throw loan exception.
     */
    @Test
    void testExecute_whenDueDateNotAfterCurrentDueDate_thenThrowLoanException() {

        // Given
        final var existingDueDate = LocalDate.now().plusDays(10);
        final var updateLoanRenewCommand = UpdateLoanRenewCommand.builder()
                .id(Instancio.create(Integer.class))
                .dueDate(existingDueDate.minusDays(1))
                .build();
        final var activeLoan = Loan.builder()
                .id(updateLoanRenewCommand.id())
                .userId(Instancio.create(Integer.class))
                .bookId(Instancio.create(Integer.class))
                .dueDate(existingDueDate)
                .status(LoanStatus.ACTIVE)
                .build();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanRenewCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand))
                .thenReturn(List.of(activeLoan));

        final var loanException = assertThrows(LoanException.class,
                () -> this.updateLoanRenewUseCase.execute(updateLoanRenewCommand));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanStatusTransitionValidationService, times(1)).execute(activeLoan.status(), LoanStatus.ACTIVE);
        verify(this.loanRenewPersistencePort, times(0)).execute(updateLoanRenewCommand);
        assertEquals(ExceptionMessageConstants.LOAN_DUE_DATE_INVALID_MESSAGE_ERROR, loanException.getMessage());
    }

    /**
     * Test execute when loan exists and due date is valid then return renewed loan.
     */
    @Test
    void testExecute_whenLoanExistsAndDueDateIsValid_thenReturnRenewedLoan() {

        // Given
        final var existingDueDate = LocalDate.now().plusDays(5);
        final var updateLoanRenewCommand = UpdateLoanRenewCommand.builder()
                .id(Instancio.create(Integer.class))
                .dueDate(existingDueDate.plusDays(14))
                .build();
        final var activeLoan = Loan.builder()
                .id(updateLoanRenewCommand.id())
                .userId(Instancio.create(Integer.class))
                .bookId(Instancio.create(Integer.class))
                .dueDate(existingDueDate)
                .status(LoanStatus.ACTIVE)
                .build();
        final var renewedLoan = Instancio.create(Loan.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanRenewCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand))
                .thenReturn(List.of(activeLoan));
        when(this.loanRenewPersistencePort.execute(updateLoanRenewCommand)).thenReturn(renewedLoan);

        final var resultLoan = this.updateLoanRenewUseCase.execute(updateLoanRenewCommand);

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanStatusTransitionValidationService, times(1)).execute(activeLoan.status(), LoanStatus.ACTIVE);
        verify(this.loanRenewPersistencePort, times(1)).execute(updateLoanRenewCommand);
        assertEquals(renewedLoan, resultLoan);
    }
}
