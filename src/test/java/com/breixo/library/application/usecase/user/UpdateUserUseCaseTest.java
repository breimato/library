package com.breixo.library.application.usecase.user;

import java.util.List;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserUpdatePersistencePort;

import org.instancio.Instancio;
import static org.instancio.Select.field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/** The Class Update User Use Case Test. */
@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    /** The update user use case. */
    @InjectMocks
    UpdateUserUseCaseImpl updateUserUseCase;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The user update persistence port. */
    @Mock
    UserUpdatePersistencePort userUpdatePersistencePort;

    /**
     * Test execute when user exists then update and return user.
     */
    @Test
    void testExecute_whenUserExists_thenUpdateAndReturnUser() {
        
        // Given
        final var updateUserCommand = Instancio.of(UpdateUserCommand.class)
            .set(field("authenticatedUserRole"), UserRole.MANAGER)
            .ignore(field("role"))
            .create();
        final var existingUser = Instancio.create(User.class);
        final var updatedUser = Instancio.create(User.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(updateUserCommand.id()).build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of(existingUser));
        when(this.userUpdatePersistencePort.execute(updateUserCommand)).thenReturn(updatedUser);
        final var result = this.updateUserUseCase.execute(updateUserCommand);

        // Then
        assertEquals(updatedUser, result);
    }

    /**
     * Test execute when user not found then throw user exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {
        
        // Given
        final var updateUserCommand = Instancio.create(UpdateUserCommand.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(updateUserCommand.id()).build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of());
        final var exception = assertThrows(UserException.class,
                () -> this.updateUserUseCase.execute(updateUserCommand));

        // Then
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }
}
