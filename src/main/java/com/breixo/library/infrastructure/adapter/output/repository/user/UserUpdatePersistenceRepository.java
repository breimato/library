package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.UserEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class User Update Persistence Repository. */
@Component
@RequiredArgsConstructor
public class UserUpdatePersistenceRepository implements UserUpdatePersistencePort {

    /** The user my batis mapper. */
    private final UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    private final UserEntityMapper userEntityMapper;

    /** {@inheritDoc} */
    @Override
    public User execute(@Valid @NotNull final UpdateUserCommand updateUserCommand) {
        this.update(updateUserCommand);
        return this.find(updateUserCommand.id());
    }

    /**
     * Update.
     *
     * @param updateUserCommand the update user command.
     */
    private void update(final UpdateUserCommand updateUserCommand) {

        final var userEntity = this.userEntityMapper.toUserEntity(updateUserCommand);

        try {
            this.userMyBatisMapper.update(userEntity);

        } catch (final Exception exception) {
            throw new UserException(
                    ExceptionMessageConstants.USER_UPDATE_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.USER_UPDATE_ERROR_MESSAGE_ERROR);
        }
    }

    /**
     * Find.
     *
     * @param id the user identifier.
     * @return the user.
     */
    private User find(final Long id) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(id).build();

        final var userEntities = this.userMyBatisMapper.find(userSearchCriteriaCommand);

        return this.userEntityMapper.toUser(userEntities.getFirst());
    }
}
