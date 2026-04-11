package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.fine.enums.FineStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Fine Status Mapper Test. */
@ExtendWith(MockitoExtension.class)
class FineStatusMapperTest {

    /** The fine status mapper. */
    @InjectMocks
    FineStatusMapperImpl fineStatusMapper;

    /**
     * Test to status id when status is pending then return zero.
     */
    @Test
    void testToStatusId_whenStatusIsPending_thenReturnZero() {
        // When / Then
        assertEquals(FineStatus.PENDING.getId(), this.fineStatusMapper.toStatusId(FineStatus.PENDING));
    }

    /**
     * Test to status id when status is paid then return one.
     */
    @Test
    void testToStatusId_whenStatusIsPaid_thenReturnOne() {
        // When / Then
        assertEquals(FineStatus.PAID.getId(), this.fineStatusMapper.toStatusId(FineStatus.PAID));
    }

    /**
     * Test to status id when status is waived then return two.
     */
    @Test
    void testToStatusId_whenStatusIsWaived_thenReturnTwo() {
        // When / Then
        assertEquals(FineStatus.WAIVED.getId(), this.fineStatusMapper.toStatusId(FineStatus.WAIVED));
    }

    /**
     * Test to fine status when integer id is pending then return pending.
     */
    @Test
    void testToFineStatus_whenIntegerIdIsPending_thenReturnPending() {
        // When / Then
        assertEquals(FineStatus.PENDING, this.fineStatusMapper.toFineStatus(FineStatus.PENDING.getId()));
    }

    /**
     * Test to fine status when integer id is paid then return paid.
     */
    @Test
    void testToFineStatus_whenIntegerIdIsPaid_thenReturnPaid() {
        // When / Then
        assertEquals(FineStatus.PAID, this.fineStatusMapper.toFineStatus(FineStatus.PAID.getId()));
    }

    /**
     * Test to fine status when integer id is waived then return waived.
     */
    @Test
    void testToFineStatus_whenIntegerIdIsWaived_thenReturnWaived() {
        // When / Then
        assertEquals(FineStatus.WAIVED, this.fineStatusMapper.toFineStatus(FineStatus.WAIVED.getId()));
    }

    /**
     * Test to status id when fine status is null then return null.
     */
    @Test
    void testToStatusId_whenFineStatusIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineStatusMapper.toStatusId(null));
    }

    /**
     * Test to fine status when integer id is null then return null.
     */
    @Test
    void testToFineStatus_whenIntegerIdIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineStatusMapper.toFineStatus(null));
    }
}
