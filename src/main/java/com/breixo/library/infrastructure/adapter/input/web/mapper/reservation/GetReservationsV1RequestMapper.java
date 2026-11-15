package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.command.reservation.ReservationSearchCriteriaCommand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Reservations V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetReservationsV1RequestMapper {

    /** To Reservation search criteria command. */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    ReservationSearchCriteriaCommand toReservationSearchCriteriaCommand(Integer id, Integer userId, Integer bookId, Integer loanId, Integer status);
}
