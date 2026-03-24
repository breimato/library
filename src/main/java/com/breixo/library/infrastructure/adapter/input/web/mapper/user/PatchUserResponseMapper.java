package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Response;

import org.mapstruct.Mapper;

/** The Interface Patch User Response Mapper. */
@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PatchUserResponseMapper {

    /**
     * To patch user v1 response.
     *
     * @param user The user.
     * @return The patch user v1 response dto.
     */
    PatchUserV1Response toPatchUserV1Response(User user);
}
