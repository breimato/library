package com.breixo.library.infrastructure.adapter.input.web.mapper.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchUserV1Request;
import com.breixo.library.infrastructure.mapper.UserRoleMapper;
import com.breixo.library.infrastructure.mapper.UserStatusMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Patch User Request Mapper. */
@Mapper(componentModel = "spring", uses = {UserStatusMapper.class, UserRoleMapper.class})
public interface PatchUserRequestMapper {

    /**
     * To update user command.
     *
     * @param id                  The user identifier.
     * @param patchUserV1RequestDto The patch user v1 request dto.
     * @return The update user command.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "patchUserV1RequestDto.name", target = "name")
    @Mapping(source = "patchUserV1RequestDto.phone", target = "phone")
    @Mapping(source = "patchUserV1RequestDto.status", target = "status")
    @Mapping(source = "patchUserV1RequestDto.role", target = "role")
    @Mapping(target = "authenticatedUserId", ignore = true)
    @Mapping(target = "authenticatedUserRole", ignore = true)
    UpdateUserCommand toUpdateUserCommand(Integer id, PatchUserV1Request patchUserV1RequestDto);
}
