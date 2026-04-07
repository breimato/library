package com.breixo.library.application.event.loanrequest;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.event.loanrequest.LoanRequestApprovedDomainEvent;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Creation On Request Approved Event Listener Test. */
@ExtendWith(MockitoExtension.class)
class LoanCreationOnRequestApprovedEventListenerTest {

    /** The loan creation on request approved event listener. */
    @InjectMocks
    LoanCreationOnRequestApprovedEventListener loanCreationOnRequestApprovedEventListener;

    /** The create loan use case. */
    @Mock
    CreateLoanUseCase createLoanUseCase;

    /** The create loan command captor. */
    @Captor
    ArgumentCaptor<CreateLoanCommand> createLoanCommandCaptor;

    /**
     * Test handle when loan request approved domain event received then create loan.
     */
    @Test
    void testHandle_whenEventReceived_thenCreateLoan() {

        // Given
        final var loanRequestApprovedDomainEvent = new LoanRequestApprovedDomainEvent(10, 20);

        // When
        when(this.createLoanUseCase.execute(createLoanCommandCaptor.capture())).thenReturn(null);
        
        this.loanCreationOnRequestApprovedEventListener.handle(loanRequestApprovedDomainEvent);

        // Then
        verify(this.createLoanUseCase, times(1)).execute(createLoanCommandCaptor.capture());
        
        final var capturedCreateLoanCommand = createLoanCommandCaptor.getValue();
        assertEquals(10, capturedCreateLoanCommand.bookId());
        assertEquals(20, capturedCreateLoanCommand.userId());
        assertNotNull(capturedCreateLoanCommand.dueDate());
    }
}
