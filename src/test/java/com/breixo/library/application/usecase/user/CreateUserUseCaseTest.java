package com.breixo.library.application.usecase.user;

import java.util.List;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.user.UserCreationPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/** The Class Create User Use Case Test. */
@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    /** The create user use case. */
    @InjectMocks
    CreateUserUseCaseImpl createUserUseCase;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user creation persistence port. */
    @Mock
    UserCreationPersistencePort userCreationPersistencePort;

    /**
     * Test execute when email does not exist then create and return user.
     */
    @Test
    void testExecute_whenEmailDoesNotExist_thenCreateAndReturnUser() {
        
        // Given
        final var createUserCommand = Instancio.create(CreateUserCommand.class);
        final var user = Instancio.create(User.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().email(createUserCommand.email()).build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of());
        when(this.userCreationPersistencePort.execute(createUserCommand)).thenReturn(user);
        final var result = this.createUserUseCase.execute(createUserCommand);

        // Then
        assertEquals(user, result);
    }

    /**
     * Test execute when email already exists then throw user exception.
     */
    @Test
    void testExecute_whenEmailAlreadyExists_thenThrowUserException() {
        
        // Given
        final var createUserCommand = Instancio.create(CreateUserCommand.class);
        final var user = Instancio.create(User.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().email(createUserCommand.email()).build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of(user));
        final var exception = assertThrows(UserException.class,
                () -> this.createUserUseCase.execute(createUserCommand));

        // Then
        assertEquals(ExceptionMessageConstants.USER_EMAIL_ALREADY_EXISTS_MESSAGE_ERROR, exception.getMessage());
    }
}
