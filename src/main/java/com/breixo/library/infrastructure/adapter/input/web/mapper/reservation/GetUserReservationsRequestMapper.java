package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.command.reservation.GetUserReservationsCommand;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Get User Reservations Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetUserReservationsRequestMapper {

    /**
     * To get user reservations command.
     *
     * @param requesterId the requester id (from X-Requester-Id header).
     * @param userId      the user id (from path variable).
     * @return the get user reservations command.
     */
    @Mapping(source = "requesterId", target = "requesterId")
    @Mapping(source = "userId", target = "userId")
    GetUserReservationsCommand toGetUserReservationsCommand(Integer requesterId, Integer userId);
}
