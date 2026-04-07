package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.command.fine.GetUserFinesCommand;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Get User Fines Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetUserFinesRequestMapper {

    /**
     * To get user fines command.
     *
     * @param requesterId the requester id (from X-Requester-Id header).
     * @param userId      the user id (from path variable).
     * @return the get user fines command.
     */
    @Mapping(source = "requesterId", target = "requesterId")
    @Mapping(source = "userId", target = "userId")
    GetUserFinesCommand toGetUserFinesCommand(Integer requesterId, Integer userId);
}
