package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import java.util.List;

import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1;
import com.breixo.library.infrastructure.mapper.ReservationStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Reservation Mapper. */
@Mapper(componentModel = "spring", uses = ReservationStatusMapper.class)
public interface ReservationMapper {

    /**
     * To reservation V1.
     *
     * @param reservation the reservation.
     * @return the reservation V1 dto.
     */
    @Mapping(source = "status", target = "status")
    ReservationV1 toReservationV1(Reservation reservation);

    /**
     * To reservation V1 list.
     *
     * @param reservations the reservations.
     * @return the reservation V1 list.
     */
    List<ReservationV1> toReservationV1List(List<Reservation> reservations);
}
