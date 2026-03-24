package com.breixo.library.infrastructure.adapter.output.mapper;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.infrastructure.adapter.output.entities.UserEntity;

import com.breixo.library.infrastructure.mapper.UserStatusMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface User Entity Mapper. */
@Mapper(componentModel = "spring", uses = {UserStatusMapper.class})
public interface UserEntityMapper {

    /**
     * To user.
     *
     * @param userEntity the user entity.
     * @return the user.
     */
    User toUser(UserEntity userEntity);

    /**
     * To user entity.
     *
     * @param command the create user command.
     * @return the user entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "active")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity toUserEntity(CreateUserCommand command);
}
