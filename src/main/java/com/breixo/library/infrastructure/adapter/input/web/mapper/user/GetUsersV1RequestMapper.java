package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetUsersV1Request;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Users V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetUsersV1RequestMapper {

    /**
     * To user search criteria command.
     *
     * @param getUsersV1Request the get users V1 request.
     * @return the user search criteria command.
     */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    UserSearchCriteriaCommand toUserSearchCriteriaCommand(GetUsersV1Request getUsersV1Request);
}
