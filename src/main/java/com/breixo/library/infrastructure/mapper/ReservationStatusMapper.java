package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.reservation.enums.ReservationStatus;

import org.mapstruct.Mapper;

/** The Interface Reservation Status Mapper. */
@Mapper(componentModel = "spring")
public interface ReservationStatusMapper {

    /**
     * To status id.
     *
     * @param reservationStatus the reservation status.
     * @return the integer.
     */
    default Integer toStatusId(final ReservationStatus reservationStatus) {
        return reservationStatus.getId();
    }

    /**
     * To reservation status.
     *
     * @param id the id.
     * @return the reservation status.
     */
    default ReservationStatus toReservationStatus(final Integer id) {
        return ReservationStatus.values()[id];
    }
}
