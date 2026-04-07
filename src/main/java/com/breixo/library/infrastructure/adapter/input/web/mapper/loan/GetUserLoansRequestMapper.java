package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.command.loan.GetUserLoansCommand;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Get User Loans Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetUserLoansRequestMapper {

    /**
     * To get user loans command.
     *
     * @param requesterId the requester id (from X-Requester-Id header).
     * @param userId      the user id (from path variable).
     * @return the get user loans command.
     */
    @Mapping(source = "requesterId", target = "requesterId")
    @Mapping(source = "userId", target = "userId")
    GetUserLoansCommand toGetUserLoansCommand(Integer requesterId, Integer userId);
}
