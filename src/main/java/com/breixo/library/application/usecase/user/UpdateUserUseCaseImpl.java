package com.breixo.library.application.usecase.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.AuthorizationException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.user.UpdateUserUseCase;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserUpdatePersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public User execute(@Valid @NotNull final UpdateUserCommand updateUserCommand) {

        this.validateUserExists(updateUserCommand);

        this.validateAuthorization(updateUserCommand);

        return this.userUpdatePersistencePort.execute(updateUserCommand);
    }

    /**
     * Validate user exists.
     *
     * @param updateUserCommand the update user command
     */
    private void validateUserExists(final UpdateUserCommand updateUserCommand) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(updateUserCommand.id()).build();
        final var users = this.userRetrievalPersistencePort.find(userSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(users)) {
            throw new UserException(
                    ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR);
        }
    }

    /**
     * Validate that the authenticated user is authorized to perform the update.
     *
     * @param command the update command
     */
    private void validateAuthorization(final UpdateUserCommand command) {

        if (UserRole.NORMAL.equals(command.authenticatedUserRole())) {

            if (!command.id().equals(command.authenticatedUserId())) {
                throw new AuthorizationException(
                        ExceptionMessageConstants.AUTH_RESOURCE_OWNERSHIP_CODE_ERROR,
                        ExceptionMessageConstants.AUTH_RESOURCE_OWNERSHIP_MESSAGE_ERROR);
            }

            if (command.role() != null) {
                throw new AuthorizationException(
                        ExceptionMessageConstants.AUTH_CANNOT_MODIFY_OWN_ROLE_CODE_ERROR,
                        ExceptionMessageConstants.AUTH_CANNOT_MODIFY_OWN_ROLE_MESSAGE_ERROR);
            }

            if (command.status() != null) {
                throw new AuthorizationException(
                        ExceptionMessageConstants.AUTH_CANNOT_MODIFY_OWN_ROLE_CODE_ERROR,
                        ExceptionMessageConstants.AUTH_CANNOT_MODIFY_OWN_ROLE_MESSAGE_ERROR);
            }
        }

        if (UserRole.MANAGER.equals(command.authenticatedUserRole()) && command.role() != null) {
            throw new AuthorizationException(
                    ExceptionMessageConstants.AUTH_INSUFFICIENT_ROLE_CODE_ERROR,
                    ExceptionMessageConstants.AUTH_INSUFFICIENT_ROLE_MESSAGE_ERROR);
        }
    }
}
