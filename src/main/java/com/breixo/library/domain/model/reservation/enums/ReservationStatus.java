package com.breixo.library.domain.model.reservation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The Enum Reservation Status. */
@Getter
@RequiredArgsConstructor
public enum ReservationStatus {

    /** The Pending. */
    PENDING(0),

    /** The Notified. */
    NOTIFIED(1),

    /** The Fulfilled. */
    FULFILLED(2),

    /** The Expired. */
    EXPIRED(3),

    /** The Cancelled. */
    CANCELLED(4);

    /** The id. */
    private final Integer id;
}
