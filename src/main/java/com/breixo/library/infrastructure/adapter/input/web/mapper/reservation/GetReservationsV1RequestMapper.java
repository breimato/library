package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetReservationsV1Request;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Reservations V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetReservationsV1RequestMapper {

    /**
     * To reservation search criteria command.
     *
     * @param getReservationsV1Request the get reservations V1 request.
     * @return the reservation search criteria command.
     */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    ReservationSearchCriteriaCommand toReservationSearchCriteriaCommand(GetReservationsV1Request getReservationsV1Request);
}
