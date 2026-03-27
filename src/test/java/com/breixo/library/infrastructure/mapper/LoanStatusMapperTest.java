package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.loan.enums.LoanStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Status Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanStatusMapperTest {

    /** The loan status mapper. */
    @InjectMocks
    LoanStatusMapperImpl loanStatusMapper;

    /**
     * Test to status id when status is active then return zero.
     */
    @Test
    void testToStatusId_whenStatusIsActive_thenReturnZero() {
        // When / Then
        assertEquals(LoanStatus.ACTIVE.getId(), this.loanStatusMapper.toStatusId(LoanStatus.ACTIVE));
    }

    /**
     * Test to status id when status is returned then return one.
     */
    @Test
    void testToStatusId_whenStatusIsReturned_thenReturnOne() {
        // When / Then
        assertEquals(LoanStatus.RETURNED.getId(), this.loanStatusMapper.toStatusId(LoanStatus.RETURNED));
    }

    /**
     * Test to status id when status is overdue then return two.
     */
    @Test
    void testToStatusId_whenStatusIsOverdue_thenReturnTwo() {
        // When / Then
        assertEquals(LoanStatus.OVERDUE.getId(), this.loanStatusMapper.toStatusId(LoanStatus.OVERDUE));
    }

    /**
     * Test to loan status when integer id is active then return active.
     */
    @Test
    void testToLoanStatus_whenIntegerIdIsActive_thenReturnActive() {
        // When / Then
        assertEquals(LoanStatus.ACTIVE, this.loanStatusMapper.toLoanStatus(LoanStatus.ACTIVE.getId()));
    }

    /**
     * Test to loan status when integer id is returned then return returned.
     */
    @Test
    void testToLoanStatus_whenIntegerIdIsReturned_thenReturnReturned() {
        // When / Then
        assertEquals(LoanStatus.RETURNED, this.loanStatusMapper.toLoanStatus(LoanStatus.RETURNED.getId()));
    }

    /**
     * Test to loan status when integer id is overdue then return overdue.
     */
    @Test
    void testToLoanStatus_whenIntegerIdIsOverdue_thenReturnOverdue() {
        // When / Then
        assertEquals(LoanStatus.OVERDUE, this.loanStatusMapper.toLoanStatus(LoanStatus.OVERDUE.getId()));
    }
}
