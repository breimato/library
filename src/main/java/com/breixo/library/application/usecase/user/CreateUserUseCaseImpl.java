package com.breixo.library.application.usecase.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.user.CreateUserUseCase;
import com.breixo.library.domain.port.output.user.UserCreationPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/** The Class Create User Use Case Impl. */
@Component
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user creation persistence port. */
    private final UserCreationPersistencePort userCreationPersistencePort;

    /** {@inheritDoc} */
    @Override
    public User execute(@Valid @NotNull final CreateUserCommand createUserCommand) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().email(createUserCommand.email()).build();

        final var emailAlreadyExists = this.userRetrievalPersistencePort.execute(userSearchCriteriaCommand).isPresent();

        if (emailAlreadyExists) {
            throw new UserException(
                    ExceptionMessageConstants.USER_EMAIL_ALREADY_EXISTS_CODE_ERROR,
                    ExceptionMessageConstants.USER_EMAIL_ALREADY_EXISTS_MESSAGE_ERROR,
                    HttpStatus.CONFLICT);
        }

        return this.userCreationPersistencePort.execute(createUserCommand);
    }
}
