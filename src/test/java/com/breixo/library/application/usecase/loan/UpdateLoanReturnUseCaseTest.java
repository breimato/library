package com.breixo.library.application.usecase.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
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
     * Test execute when loan exists then update and return loan.
     */
    @Test
    void testExecute_whenLoanExists_thenUpdateAndReturnLoan() {
        
        // Given
        final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);
        final var loanOne = Instancio.create(Loan.class);
        final var loanTwo = Instancio.create(Loan.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .id(updateLoanReturnCommand.id())
                .build();

        // When
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(List.of(loanOne));
        when(this.loanUpdatePersistencePort.execute(updateLoanReturnCommand)).thenReturn(loanTwo);
        final var loan = this.updateLoanReturnUseCase.execute(updateLoanReturnCommand);

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanUpdatePersistencePort, times(1)).execute(updateLoanReturnCommand);
        assertEquals(loanTwo, loan);
    }
}
