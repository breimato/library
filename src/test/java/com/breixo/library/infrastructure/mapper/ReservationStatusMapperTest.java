package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/** The Class Reservation Status Mapper Test. */
@ExtendWith(MockitoExtension.class)
class ReservationStatusMapperTest {

    /** The reservation status mapper. */
    @InjectMocks
    ReservationStatusMapperImpl reservationStatusMapper;

    /**
     * Test to status id when status is pending then return zero.
     */
    @Test
    void testToStatusId_whenStatusIsPending_thenReturnZero() {
        // When / Then
        assertEquals(ReservationStatus.PENDING.getId(), this.reservationStatusMapper.toStatusId(ReservationStatus.PENDING));
    }

    /**
     * Test to status id when status is notified then return one.
     */
    @Test
    void testToStatusId_whenStatusIsNotified_thenReturnOne() {
        // When / Then
        assertEquals(ReservationStatus.NOTIFIED.getId(), this.reservationStatusMapper.toStatusId(ReservationStatus.NOTIFIED));
    }

    /**
     * Test to status id when status is fulfilled then return two.
     */
    @Test
    void testToStatusId_whenStatusIsFulfilled_thenReturnTwo() {
        // When / Then
        assertEquals(ReservationStatus.FULFILLED.getId(), this.reservationStatusMapper.toStatusId(ReservationStatus.FULFILLED));
    }

    /**
     * Test to status id when status is expired then return three.
     */
    @Test
    void testToStatusId_whenStatusIsExpired_thenReturnThree() {
        // When / Then
        assertEquals(ReservationStatus.EXPIRED.getId(), this.reservationStatusMapper.toStatusId(ReservationStatus.EXPIRED));
    }

    /**
     * Test to status id when status is cancelled then return four.
     */
    @Test
    void testToStatusId_whenStatusIsCancelled_thenReturnFour() {
        // When / Then
        assertEquals(ReservationStatus.CANCELLED.getId(), this.reservationStatusMapper.toStatusId(ReservationStatus.CANCELLED));
    }

    /**
     * Test to reservation status when integer id is pending then return pending.
     */
    @Test
    void testToReservationStatus_whenIntegerIdIsPending_thenReturnPending() {
        // When / Then
        assertEquals(ReservationStatus.PENDING, this.reservationStatusMapper.toReservationStatus(ReservationStatus.PENDING.getId()));
    }

    /**
     * Test to reservation status when integer id is notified then return notified.
     */
    @Test
    void testToReservationStatus_whenIntegerIdIsNotified_thenReturnNotified() {
        // When / Then
        assertEquals(ReservationStatus.NOTIFIED, this.reservationStatusMapper.toReservationStatus(ReservationStatus.NOTIFIED.getId()));
    }

    /**
     * Test to reservation status when integer id is fulfilled then return fulfilled.
     */
    @Test
    void testToReservationStatus_whenIntegerIdIsFulfilled_thenReturnFulfilled() {
        // When / Then
        assertEquals(ReservationStatus.FULFILLED, this.reservationStatusMapper.toReservationStatus(ReservationStatus.FULFILLED.getId()));
    }

    /**
     * Test to reservation status when integer id is expired then return expired.
     */
    @Test
    void testToReservationStatus_whenIntegerIdIsExpired_thenReturnExpired() {
        // When / Then
        assertEquals(ReservationStatus.EXPIRED, this.reservationStatusMapper.toReservationStatus(ReservationStatus.EXPIRED.getId()));
    }

    /**
     * Test to reservation status when integer id is cancelled then return cancelled.
     */
    @Test
    void testToReservationStatus_whenIntegerIdIsCancelled_thenReturnCancelled() {
        // When / Then
        assertEquals(ReservationStatus.CANCELLED, this.reservationStatusMapper.toReservationStatus(ReservationStatus.CANCELLED.getId()));
    }

    /**
     * Test to status id when reservation status is null then return null.
     */
    @Test
    void testToStatusId_whenReservationStatusIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationStatusMapper.toStatusId(null));
    }

    /**
     * Test to reservation status when integer id is null then return null.
     */
    @Test
    void testToReservationStatus_whenIntegerIdIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.reservationStatusMapper.toReservationStatus(null));
    }
}
