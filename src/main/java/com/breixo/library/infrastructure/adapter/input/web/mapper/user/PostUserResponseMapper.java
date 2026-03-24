package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostUserV1ResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Post User Response Mapper. */
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PostUserResponseMapper {

    /**
     * To post user v1 response.
     *
     * @param user the user.
     * @return the post user v1 response dto.
     */
    @Mapping(target = "user", source = "user")
    PostUserV1ResponseDto toPostUserV1Response(User user);
}
