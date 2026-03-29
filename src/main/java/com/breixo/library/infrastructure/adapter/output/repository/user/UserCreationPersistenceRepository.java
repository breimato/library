package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.UserEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class User Creation Persistence Repository. */
@Component
@RequiredArgsConstructor
public class UserCreationPersistenceRepository implements UserCreationPersistencePort {

    /** The user my batis mapper. */
    private final UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    private final UserEntityMapper userEntityMapper;

    /** {@inheritDoc} */
    @Override
    public User execute(@Valid @NotNull final CreateUserCommand createUserCommand) {

        final var userEntity = this.userEntityMapper.toUserEntity(createUserCommand);

        this.userMyBatisMapper.insert(userEntity);

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(userEntity.getId()).build();
        final var createdUserEntity = this.userMyBatisMapper.find(userSearchCriteriaCommand).getFirst();

        return this.userEntityMapper.toUser(createdUserEntity);
    }
}
