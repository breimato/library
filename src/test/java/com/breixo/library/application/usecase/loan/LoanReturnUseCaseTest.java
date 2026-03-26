package com.breixo.library.application.usecase.loan;

import java.util.Optional;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.ReturnLoanCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanUpdatePersistencePort;

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

/** The Class Loan Return Use Case Test. */
@ExtendWith(MockitoExtension.class)
class LoanReturnUseCaseTest {

    /** The loan return use case. */
    @InjectMocks
    LoanReturnUseCaseImpl loanReturnUseCase;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan update persistence port. */
    @Mock
    LoanUpdatePersistencePort loanUpdatePersistencePort;

    /**
     * Test execute when loan not found then throw loan exception.
     */
    @Test
    void testExecute_whenLoanNotFound_thenThrowLoanException() {
        // Given
        final var returnLoanCommand = Instancio.create(ReturnLoanCommand.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(returnLoanCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(Optional.empty());
        final var exception = assertThrows(LoanException.class,
                () -> this.loanReturnUseCase.execute(returnLoanCommand));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanUpdatePersistencePort, times(0)).execute(returnLoanCommand);
        assertEquals(ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test execute when loan exists then update and return loan.
     */
    @Test
    void testExecute_whenLoanExists_thenUpdateAndReturnLoan() {
        // Given
        final var returnLoanCommand = Instancio.create(ReturnLoanCommand.class);
        final var existingLoan = Instancio.create(Loan.class);
        final var updatedLoan = Instancio.create(Loan.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(returnLoanCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand))
                .thenReturn(Optional.of(existingLoan));
        when(this.loanUpdatePersistencePort.execute(returnLoanCommand)).thenReturn(updatedLoan);
        final var result = this.loanReturnUseCase.execute(returnLoanCommand);

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanUpdatePersistencePort, times(1)).execute(returnLoanCommand);
        assertEquals(updatedLoan, result);
    }
}
