package com.breixo.library.application.usecase.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.user.UpdateUserUseCase;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/** The Class Update User Use Case Impl. */
@Component
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user update persistence port. */
    private final UserUpdatePersistencePort userUpdatePersistencePort;

    /** {@inheritDoc} */
    @Override
    public User execute(@Valid @NotNull final UpdateUserCommand updateUserCommand) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(updateUserCommand.id()).build();
        final var users = this.userRetrievalPersistencePort.find(userSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(users)) {
            throw new UserException(
                    ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR);
        }

        return this.userUpdatePersistencePort.execute(updateUserCommand);
    }
}
