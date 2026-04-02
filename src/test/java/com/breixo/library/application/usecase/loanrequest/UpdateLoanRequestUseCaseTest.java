package com.breixo.library.application.usecase.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;

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

    /**
     * Test execute when loan request exists then update and return loan request.
     */
    @Test
    void testExecute_whenLoanRequestExists_thenUpdateAndReturnLoanRequest() {

        // Given
        final var updateLoanRequestCommand = Instancio.create(UpdateLoanRequestCommand.class);
        final var existingLoanRequest = Instancio.create(LoanRequest.class);
        final var updatedLoanRequest = Instancio.create(LoanRequest.class);

        // When
        when(this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id()))
                .thenReturn(existingLoanRequest);
        when(this.loanRequestUpdatePersistencePort.execute(updateLoanRequestCommand)).thenReturn(updatedLoanRequest);
        final var result = this.updateLoanRequestUseCase.execute(updateLoanRequestCommand);

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).findById(updateLoanRequestCommand.id());
        verify(this.loanRequestUpdatePersistencePort, times(1)).execute(updateLoanRequestCommand);
        assertEquals(updatedLoanRequest, result);
    }

    /**
     * Test execute when loan request not found then throw loan request exception.
     */
    @Test
    void testExecute_whenLoanRequestNotFound_thenThrowLoanRequestException() {

        // Given
        final var updateLoanRequestCommand = Instancio.create(UpdateLoanRequestCommand.class);

        // When
        when(this.loanRequestRetrievalPersistencePort.findById(updateLoanRequestCommand.id()))
                .thenThrow(new LoanRequestException(
                        ExceptionMessageConstants.LOAN_REQUEST_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.LOAN_REQUEST_NOT_FOUND_MESSAGE_ERROR));
        final var exception = assertThrows(LoanRequestException.class,
                () -> this.updateLoanRequestUseCase.execute(updateLoanRequestCommand));

        // Then
        verify(this.loanRequestRetrievalPersistencePort, times(1)).findById(updateLoanRequestCommand.id());
        verify(this.loanRequestUpdatePersistencePort, times(0)).execute(updateLoanRequestCommand);
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }
}
