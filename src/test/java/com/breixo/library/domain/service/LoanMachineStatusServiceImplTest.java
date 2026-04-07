package com.breixo.library.domain.service;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.service.loan.LoanMachineStatusServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** The Class Loan Machine Status Service Impl Test. */
@ExtendWith(MockitoExtension.class)
class LoanMachineStatusServiceImplTest {

    /** The loan machine status service. */
    @InjectMocks
    LoanMachineStatusServiceImpl loanMachineStatusService;

    /**
     * Test execute when same status then return.
     */
    @Test
    void testExecute_whenSameStatus_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.ACTIVE;
        final var newStatus = LoanStatus.ACTIVE;

        // When
        this.loanMachineStatusService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test execute when current status is returned then throw loan
     * exception.
     */
    @Test
    void testExecute_whenCurrentStatusIsReturned_thenThrowLoanException() {

        // Given
        final var currentStatus = LoanStatus.RETURNED;
        final var newStatus = LoanStatus.ACTIVE;

        // When
        final var exception = assertThrows(LoanException.class,
                () -> this.loanMachineStatusService.execute(currentStatus, newStatus));

        // Then
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test execute when active to returned then return.
     */
    @Test
    void testExecute_whenActiveToReturned_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.ACTIVE;
        final var newStatus = LoanStatus.RETURNED;

        // When
        this.loanMachineStatusService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test execute when active to overdue then return.
     */
    @Test
    void testExecute_whenActiveToOverdue_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.ACTIVE;
        final var newStatus = LoanStatus.OVERDUE;

        // When
        this.loanMachineStatusService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test execute when overdue to returned then return.
     */
    @Test
    void testExecute_whenOverdueToReturned_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.OVERDUE;
        final var newStatus = LoanStatus.RETURNED;

        // When
        this.loanMachineStatusService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test execute when overdue to active then return.
     */
    @Test
    void testExecute_whenOverdueToActive_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.OVERDUE;
        final var newStatus = LoanStatus.ACTIVE;

        // When
        this.loanMachineStatusService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }
}
