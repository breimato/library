package com.breixo.library.infrastructure.adapter.output.repository.user;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.UserEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.UserMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class User Retrieval Persistence Repository. */
@Component
@RequiredArgsConstructor
public class UserRetrievalPersistenceRepository implements UserRetrievalPersistencePort {

    /** The user my batis mapper. */
    private final UserMyBatisMapper userMyBatisMapper;

    /** The user entity mapper. */
    private final UserEntityMapper userEntityMapper;

    /** {@inheritDoc} */
    @Override
    public User execute(@Valid @NotNull final UserSearchCriteriaCommand userSearchCriteriaCommand) {

        final var userEntities = this.userMyBatisMapper.find(userSearchCriteriaCommand);

        if (userEntities.isEmpty()) {
            throw new UserException(
                    ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR);
        }
        return this.userEntityMapper.toUser(userEntities.getFirst());
    }
}
