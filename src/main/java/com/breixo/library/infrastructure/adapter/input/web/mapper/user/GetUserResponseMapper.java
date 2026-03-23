package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetUserIdV1Response;

import org.mapstruct.Mapper;

/** The Interface Get User Response Mapper. */
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface GetUserResponseMapper {

    /**
     * To get user id v1 response.
     *
     * @param user The user.
     * @return The get user id v1 response.
     */
    GetUserIdV1Response toGetUserIdV1Response(User user);
}
