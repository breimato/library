package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Loan Request Status Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestStatusMapperTest {

    /** The loan request status mapper. */
    @InjectMocks
    LoanRequestStatusMapperImpl loanRequestStatusMapper;

    /**
     * Test to status id when status is pending then return zero.
     */
    @Test
    void testToStatusId_whenStatusIsPending_thenReturnZero() {
        // When / Then
        assertEquals(LoanRequestStatus.PENDING.getId(), this.loanRequestStatusMapper.toStatusId(LoanRequestStatus.PENDING));
    }

    /**
     * Test to status id when status is approved then return one.
     */
    @Test
    void testToStatusId_whenStatusIsApproved_thenReturnOne() {
        // When / Then
        assertEquals(LoanRequestStatus.APPROVED.getId(), this.loanRequestStatusMapper.toStatusId(LoanRequestStatus.APPROVED));
    }

    /**
     * Test to status id when status is rejected then return two.
     */
    @Test
    void testToStatusId_whenStatusIsRejected_thenReturnTwo() {
        // When / Then
        assertEquals(LoanRequestStatus.REJECTED.getId(), this.loanRequestStatusMapper.toStatusId(LoanRequestStatus.REJECTED));
    }

    /**
     * Test to status id when status is cancelled then return three.
     */
    @Test
    void testToStatusId_whenStatusIsCancelled_thenReturnThree() {
        // When / Then
        assertEquals(LoanRequestStatus.CANCELLED.getId(), this.loanRequestStatusMapper.toStatusId(LoanRequestStatus.CANCELLED));
    }

    /**
     * Test to loan request status when integer id is pending then return pending.
     */
    @Test
    void testToLoanRequestStatus_whenIntegerIdIsPending_thenReturnPending() {
        // When / Then
        assertEquals(LoanRequestStatus.PENDING, this.loanRequestStatusMapper.toLoanRequestStatus(LoanRequestStatus.PENDING.getId()));
    }

    /**
     * Test to loan request status when integer id is approved then return approved.
     */
    @Test
    void testToLoanRequestStatus_whenIntegerIdIsApproved_thenReturnApproved() {
        // When / Then
        assertEquals(LoanRequestStatus.APPROVED, this.loanRequestStatusMapper.toLoanRequestStatus(LoanRequestStatus.APPROVED.getId()));
    }

    /**
     * Test to loan request status when integer id is rejected then return rejected.
     */
    @Test
    void testToLoanRequestStatus_whenIntegerIdIsRejected_thenReturnRejected() {
        // When / Then
        assertEquals(LoanRequestStatus.REJECTED, this.loanRequestStatusMapper.toLoanRequestStatus(LoanRequestStatus.REJECTED.getId()));
    }

    /**
     * Test to loan request status when integer id is cancelled then return cancelled.
     */
    @Test
    void testToLoanRequestStatus_whenIntegerIdIsCancelled_thenReturnCancelled() {
        // When / Then
        assertEquals(LoanRequestStatus.CANCELLED, this.loanRequestStatusMapper.toLoanRequestStatus(LoanRequestStatus.CANCELLED.getId()));
    }
}
