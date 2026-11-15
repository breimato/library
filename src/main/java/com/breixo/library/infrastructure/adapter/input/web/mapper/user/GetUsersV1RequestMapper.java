package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/** The Interface Get Users V1 Request Mapper. */
@Mapper(componentModel = "spring")
public interface GetUsersV1RequestMapper {

    /** To User search criteria command. */
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "status", target = "statusId")
    UserSearchCriteriaCommand toUserSearchCriteriaCommand(Integer id, String name, String email, Integer status);
}
