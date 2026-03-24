package com.breixo.library.infrastructure.adapter.output.mapper;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.command.user.UpdateUserCommand;
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
    @Mapping(source = "statusId", target = "status")
    User toUser(UserEntity userEntity);

    /**
     * To user entity.
     *
     * @param createUserCommand the create user command.
     * @return the user entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statusId", ignore = true)
    UserEntity toUserEntity(CreateUserCommand createUserCommand);

    /**
     * To user entity.
     *
     * @param updateUserCommand the update user command.
     * @return the user entity.
     */
    @Mapping(source = "status", target = "statusId")
    @Mapping(target = "email", ignore = true)
    UserEntity toUserEntity(UpdateUserCommand updateUserCommand);
}
