package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.infrastructure.adapter.input.web.dto.ReservationV1Response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Reservation Response Mapper. */
@Mapper(componentModel = "spring", uses = ReservationMapper.class)
public interface ReservationResponseMapper {

    /**
     * To reservation V1 response.
     *
     * @param reservation the reservation.
     * @return the reservation V1 response.
     */
    @Mapping(target = "reservation", source = "reservation")
    ReservationV1Response toReservationV1Response(Reservation reservation);
}
