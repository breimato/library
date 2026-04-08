package com.breixo.library.infrastructure.mapper;

import java.util.Objects;

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
        if (Objects.isNull(reservationStatus)) {
            return null;
        }
        return reservationStatus.getId();
    }

    /**
     * To reservation status.
     *
     * @param id the id.
     * @return the reservation status.
     */
    default ReservationStatus toReservationStatus(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return ReservationStatus.values()[id];
    }
}
