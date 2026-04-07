package com.breixo.library.domain.service.loanrequest;

import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** The Class Loan Request Machine Status Service Impl Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestMachineStatusServiceImplTest {

    /** The loan request machine status service. */
    @InjectMocks
    LoanRequestMachineStatusServiceImpl loanRequestMachineStatusService;

    /**
     * Test execute when same status then success.
     */
    @Test
    void testExecute_whenSameStatus_thenSuccess() {
        
        // Given
        final var loanRequestStatus = LoanRequestStatus.PENDING;

        // When/Then
        assertDoesNotThrow(() -> this.loanRequestMachineStatusService.execute(loanRequestStatus, loanRequestStatus));
    }

    /**
     * Test execute when pending to approved then success.
     */
    @Test
    void testExecute_whenPendingToApproved_thenSuccess() {
        
        // Given
        final var currentLoanRequestStatus = LoanRequestStatus.PENDING;
        final var newLoanRequestStatus = LoanRequestStatus.APPROVED;

        // When/Then
        assertDoesNotThrow(() -> this.loanRequestMachineStatusService.execute(currentLoanRequestStatus, newLoanRequestStatus));
    }

    /**
     * Test execute when pending to rejected then success.
     */
    @Test
    void testExecute_whenPendingToRejected_thenSuccess() {

        // Given
        final var currentLoanRequestStatus = LoanRequestStatus.PENDING;
        final var newLoanRequestStatus = LoanRequestStatus.REJECTED;

        // When/Then
        assertDoesNotThrow(() -> this.loanRequestMachineStatusService.execute(currentLoanRequestStatus, newLoanRequestStatus));
    }

    /**
     * Test execute when pending to cancelled then success.
     */
    @Test
    void testExecute_whenPendingToCancelled_thenSuccess() {

        // Given
        final var currentLoanRequestStatus = LoanRequestStatus.PENDING;
        final var newLoanRequestStatus = LoanRequestStatus.CANCELLED;

        // When/Then
        assertDoesNotThrow(() -> this.loanRequestMachineStatusService.execute(currentLoanRequestStatus, newLoanRequestStatus));
    }

    /**
     * Test execute when invalid transition then throw exception.
     */
    @Test
    void testExecute_whenInvalidTransition_thenThrowException() {

        // Given
        final var currentLoanRequestStatus = LoanRequestStatus.APPROVED;
        final var newLoanRequestStatus = LoanRequestStatus.PENDING;

        // When
        final var ex = assertThrows(LoanRequestException.class, 
                () -> this.loanRequestMachineStatusService.execute(currentLoanRequestStatus, newLoanRequestStatus));

        // Then
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_INVALID_STATUS_TRANSITION_CODE_ERROR, ex.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_INVALID_STATUS_TRANSITION_MESSAGE_ERROR, ex.getMessage());
    }
}
