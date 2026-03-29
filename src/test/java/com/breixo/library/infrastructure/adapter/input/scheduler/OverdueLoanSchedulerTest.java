package com.breixo.library.infrastructure.adapter.input.scheduler;

import com.breixo.library.domain.port.output.loan.LoanMarkOverduePersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Overdue Loan Scheduler Test. */
@ExtendWith(MockitoExtension.class)
class OverdueLoanSchedulerTest {

    /** The Overdue Loan Scheduler. */
    @InjectMocks
    OverdueLoanScheduler overdueLoanScheduler;

    /** The Loan Mark Overdue Persistence Port. */
    @Mock
    LoanMarkOverduePersistencePort loanMarkOverduePersistencePort;

    /**
     * Test mark overdue loans when called then delegates to port.
     */
    @Test
    void testMarkOverdueLoans_whenCalled_thenDelegatesToPort() {
        // Given
        final var updatedCount = Instancio.create(Integer.class);

        // When
        when(this.loanMarkOverduePersistencePort.markOverdue()).thenReturn(updatedCount);
        this.overdueLoanScheduler.markOverdueLoans();

        // Then
        verify(this.loanMarkOverduePersistencePort, times(1)).markOverdue();
    }
}
