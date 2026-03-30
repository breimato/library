package com.breixo.library.application.usecase.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanUpdatePersistencePort;
import com.breixo.library.domain.service.FineManagementService;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    /** The fine management service. */
    @Mock
    FineManagementService fineManagementService;

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
     * Test execute when loan exists then update and process fine.
     */
    @Test
    void testExecute_whenLoanExists_thenUpdateAndProcessFine() {

        // Given
        final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);
        final var loan = Instancio.create(Loan.class);
        final var loanBeforeReturn = Loan.builder()
                .id(updateLoanReturnCommand.id())
                .userId(loan.userId())
                .bookId(loan.bookId())
                .dueDate(loan.dueDate())
                .returnDate(null)
                .status(LoanStatus.ACTIVE)
                .build();
        final var updatedLoan = Instancio.create(Loan.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanReturnCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(List.of(loanBeforeReturn));
        when(this.loanUpdatePersistencePort.execute(updateLoanReturnCommand)).thenReturn(updatedLoan);
        final var resultLoan = this.updateLoanReturnUseCase.execute(updateLoanReturnCommand);

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanUpdatePersistencePort, times(1)).execute(updateLoanReturnCommand);
        verify(this.fineManagementService, times(1)).execute(loanBeforeReturn, updateLoanReturnCommand.returnDate());
        assertEquals(updatedLoan, resultLoan);
    }

    /**
     * Test execute when loan already returned then throw loan exception.
     */
    @Test
    void testExecute_whenLoanAlreadyReturned_thenThrowLoanException() {

        // Given
        final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);
        final var loan = Instancio.create(Loan.class);
        final var returnedLoan = Loan.builder()
                .id(updateLoanReturnCommand.id())
                .userId(loan.userId())
                .bookId(loan.bookId())
                .dueDate(loan.dueDate())
                .returnDate(updateLoanReturnCommand.returnDate())
                .status(LoanStatus.RETURNED)
                .build();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanReturnCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(List.of(returnedLoan));
        final var loanException = assertThrows(LoanException.class,
                () -> this.updateLoanReturnUseCase.execute(updateLoanReturnCommand));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanUpdatePersistencePort, times(0)).execute(updateLoanReturnCommand);
        verify(this.fineManagementService, times(0)).execute(returnedLoan, updateLoanReturnCommand.returnDate());
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR, loanException.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR, loanException.getMessage());
    }
}
