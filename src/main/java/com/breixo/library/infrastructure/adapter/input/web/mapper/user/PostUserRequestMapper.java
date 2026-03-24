package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1RequestDto;

import org.mapstruct.Mapper;

/** The Interface Post User Request Mapper. */
@Mapper(componentModel = "spring")
public interface PostUserRequestMapper {

    /**
     * To create user command.
     *
     * @param postUserV1RequestDto the post user v1 request dto.
     * @return the create user command.
     */
    CreateUserCommand toCreateUserCommand(PostUserV1RequestDto postUserV1RequestDto);
}
