package com.breixo.library.infrastructure.adapter.input.web.mapper.reservation;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostReservationV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.common.DateMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Post Reservation Request Mapper. */
@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface PostReservationRequestMapper {

    /**
     * To create reservation command.
     *
     * @param postReservationV1Request the post reservation V1 request.
     * @return the create reservation command.
     */
    @Mapping(source = "status", target = "statusId")
    CreateReservationCommand toCreateReservationCommand(PostReservationV1Request postReservationV1Request);
}
