package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.UserV1ResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface User Response Mapper. */
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserResponseMapper {

    /**
     * To user v1 response.
     *
     * @param user the user.
     * @return the user v1 response dto.
     */
    @Mapping(target = "user", source = "user")
    UserV1ResponseDto toUserV1Response(User user);
}
