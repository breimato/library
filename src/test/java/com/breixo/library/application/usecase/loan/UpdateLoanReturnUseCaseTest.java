package com.breixo.library.application.usecase.loan;

import java.time.LocalDate;
import java.util.List;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.event.LoanReturnedDomainEvent;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanUpdatePersistencePort;
import com.breixo.library.domain.port.input.fine.FineManagementService;
import com.breixo.library.domain.port.input.loan.LoanStatusTransitionValidationService;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Update Loan Return Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateLoanReturnUseCaseTest {

        /** The update loan return use case. */
        @InjectMocks
        UpdateLoanReturnUseCaseImpl updateLoanReturnUseCase;

        /** The loan retrieval persistence port. */
        @Mock
        LoanRetrievalPersistencePort loanRetrievalPersistencePort;

        /** The loan update persistence port. */
        @Mock
        LoanUpdatePersistencePort loanUpdatePersistencePort;

        /** The loan status transition validation service. */
        @Mock
        LoanStatusTransitionValidationService loanStatusTransitionValidationService;

        /** The fine management service. */
        @Mock
        FineManagementService fineManagementService;

        /** The application event publisher. */
        @Mock
        ApplicationEventPublisher applicationEventPublisher;

        /**
         * Test execute when loan not found then throw loan exception.
         */
        @Test
        void testExecute_whenLoanNotFound_thenThrowLoanException() {

                // Given
                final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);
                final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                                .id(updateLoanReturnCommand.id())
                                .build();

                // When
                when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(List.of());
                final var loanException = assertThrows(LoanException.class,
                                () -> this.updateLoanReturnUseCase.execute(updateLoanReturnCommand));

                // Then
                verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
                verify(this.loanUpdatePersistencePort, times(0)).execute(updateLoanReturnCommand);
                assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR, loanException.getMessage());
        }

        /**
         * Test execute when return date is in the future then throw loan exception.
         */
        @Test
        void testExecute_whenReturnDateInFuture_thenThrowLoanException() {

                // Given
                final var updateLoanReturnCommand = UpdateLoanReturnCommand.builder()
                                .id(Instancio.create(Integer.class))
                                .returnDate(LocalDate.now().plusDays(1))
                                .build();
                final var loan = Instancio.create(Loan.class);
                final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                                .id(updateLoanReturnCommand.id())
                                .build();

                // When
                when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(List.of(loan));
                final var loanException = assertThrows(LoanException.class,
                                () -> this.updateLoanReturnUseCase.execute(updateLoanReturnCommand));

                // Then
                verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
                verify(this.loanUpdatePersistencePort, times(0)).execute(updateLoanReturnCommand);
                assertEquals(ExceptionMessageConstants.LOAN_RETURN_DATE_INVALID_MESSAGE_ERROR,
                                loanException.getMessage());
        }

        /**
         * Test execute when loan already returned then throw loan exception.
         */
        @Test
        void testExecute_whenLoanAlreadyReturned_thenThrowLoanException() {

                // Given
                final var updateLoanReturnCommand = UpdateLoanReturnCommand.builder()
                                .id(Instancio.create(Integer.class))
                                .returnDate(LocalDate.now().minusDays(1))
                                .build();
                final var returnedLoan = Instancio.create(Loan.class);
                final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                                .id(updateLoanReturnCommand.id())
                                .build();

                // When
                when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand))
                                .thenReturn(List.of(returnedLoan));
                doThrow(new LoanException(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR,
                                ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR))
                                .when(this.loanStatusTransitionValidationService)
                                .execute(returnedLoan.status(), LoanStatus.RETURNED);

                final var loanException = assertThrows(LoanException.class,
                                () -> this.updateLoanReturnUseCase.execute(updateLoanReturnCommand));

                // Then
                verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
                verify(this.loanStatusTransitionValidationService, times(1)).execute(returnedLoan.status(),
                                LoanStatus.RETURNED);
                verify(this.loanUpdatePersistencePort, times(0)).execute(updateLoanReturnCommand);
                assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR, loanException.getMessage());
        }

        /**
         * Test execute when loan exists then update and process fine.
         */
        @Test
        void testExecute_whenLoanExists_thenUpdateAndProcessFine() {

                // Given
                final var updateLoanReturnCommand = UpdateLoanReturnCommand.builder()
                                .id(Instancio.create(Integer.class))
                                .returnDate(LocalDate.now().minusDays(1))
                                .build();
                final var loanBeforeReturn = Loan.builder()
                                .id(updateLoanReturnCommand.id())
                                .userId(Instancio.create(Integer.class))
                                .bookId(Instancio.create(Integer.class))
                                .dueDate(Instancio.create(LocalDate.class))
                                .returnDate(null)
                                .status(LoanStatus.ACTIVE)
                                .build();
                final var updatedLoan = Instancio.create(Loan.class);
                final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                                .id(updateLoanReturnCommand.id())
                                .build();

                // When
                when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand))
                                .thenReturn(List.of(loanBeforeReturn));
                when(this.loanUpdatePersistencePort.execute(updateLoanReturnCommand)).thenReturn(updatedLoan);
                final var resultLoan = this.updateLoanReturnUseCase.execute(updateLoanReturnCommand);

                // Then
                verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
                verify(this.loanStatusTransitionValidationService, times(1)).execute(loanBeforeReturn.status(),
                                LoanStatus.RETURNED);
                verify(this.loanUpdatePersistencePort, times(1)).execute(updateLoanReturnCommand);
                verify(this.fineManagementService, times(1)).execute(loanBeforeReturn,
                                updateLoanReturnCommand.returnDate());
                verify(this.applicationEventPublisher, times(1)).publishEvent(LoanReturnedDomainEvent.builder()
                                .bookId(loanBeforeReturn.bookId())
                                .build());
                assertEquals(updatedLoan, resultLoan);
        }
}
