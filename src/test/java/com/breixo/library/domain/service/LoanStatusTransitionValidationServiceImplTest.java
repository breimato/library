package com.breixo.library.domain.service;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** The Class Loan Status Transition Validation Service Impl Test. */
@ExtendWith(MockitoExtension.class)
class LoanStatusTransitionValidationServiceImplTest {

    /** The loan status transition validation service. */
    @InjectMocks
    LoanStatusTransitionValidationServiceImpl loanStatusTransitionValidationService;

    /**
     * Test validate transition when same status then return.
     */
    @Test
    void testValidateTransition_whenSameStatus_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.ACTIVE;
        final var newStatus = LoanStatus.ACTIVE;

        // When
        this.loanStatusTransitionValidationService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test validate transition when current status is returned then throw loan
     * exception.
     */
    @Test
    void testValidateTransition_whenCurrentStatusIsReturned_thenThrowLoanException() {

        // Given
        final var currentStatus = LoanStatus.RETURNED;
        final var newStatus = LoanStatus.ACTIVE;

        // When
        final var exception = assertThrows(LoanException.class,
                () -> this.loanStatusTransitionValidationService.execute(currentStatus, newStatus));

        // Then
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test validate transition when active to returned then return.
     */
    @Test
    void testValidateTransition_whenActiveToReturned_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.ACTIVE;
        final var newStatus = LoanStatus.RETURNED;

        // When
        this.loanStatusTransitionValidationService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test validate transition when active to overdue then return.
     */
    @Test
    void testValidateTransition_whenActiveToOverdue_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.ACTIVE;
        final var newStatus = LoanStatus.OVERDUE;

        // When
        this.loanStatusTransitionValidationService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test validate transition when overdue to returned then return.
     */
    @Test
    void testValidateTransition_whenOverdueToReturned_thenReturn() {

        // Given
        final var currentStatus = LoanStatus.OVERDUE;
        final var newStatus = LoanStatus.RETURNED;

        // When
        this.loanStatusTransitionValidationService.execute(currentStatus, newStatus);

        // Then (no exception thrown)
    }

    /**
     * Test validate transition when overdue to active then throw loan exception.
     */
    @Test
    void testValidateTransition_whenOverdueToActive_thenThrowLoanException() {

        // Given
        final var currentStatus = LoanStatus.OVERDUE;
        final var newStatus = LoanStatus.ACTIVE;

        // When
        final var exception = assertThrows(LoanException.class,
                () -> this.loanStatusTransitionValidationService.execute(currentStatus, newStatus));

        // Then
        assertEquals(ExceptionMessageConstants.LOAN_INVALID_STATUS_TRANSITION_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_INVALID_STATUS_TRANSITION_MESSAGE_ERROR, exception.getMessage());
    }
}
