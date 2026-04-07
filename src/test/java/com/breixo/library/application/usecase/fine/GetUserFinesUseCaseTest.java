package com.breixo.library.application.usecase.fine;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.fine.GetUserFinesCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Get User Fines Use Case Test. */
@ExtendWith(MockitoExtension.class)
class GetUserFinesUseCaseTest {

    /** The get user fines use case. */
    @InjectMocks
    GetUserFinesUseCaseImpl getUserFinesUseCase;

    /** The authorization service. */
    @Mock
    AuthorizationService authorizationService;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The fine retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /**
     * Test execute when user exists then return fines.
     */
    @Test
    void testExecute_whenUserExists_thenReturnFines() {

        // Given
        final var getUserFinesCommand = Instancio.create(GetUserFinesCommand.class);
        final var user = Instancio.create(User.class);
        final var fines = Instancio.createList(Fine.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserFinesCommand.requesterId(),
                getUserFinesCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserFinesCommand.userId())).thenReturn(Optional.of(user));
        when(this.fineRetrievalPersistencePort.findByUserId(getUserFinesCommand.userId())).thenReturn(fines);

        final var result = this.getUserFinesUseCase.execute(getUserFinesCommand);

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                getUserFinesCommand.requesterId(),
                getUserFinesCommand.userId(),
                UserRole.MANAGER);
        verify(this.userRetrievalPersistencePort, times(1)).findById(getUserFinesCommand.userId());
        verify(this.fineRetrievalPersistencePort, times(1)).findByUserId(getUserFinesCommand.userId());
        assertEquals(fines, result);
    }

    /**
     * Test execute when user not found then throw user exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {

        // Given
        final var getUserFinesCommand = Instancio.create(GetUserFinesCommand.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserFinesCommand.requesterId(),
                getUserFinesCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserFinesCommand.userId())).thenReturn(Optional.empty());

        final var exception = assertThrows(UserException.class,
                () -> this.getUserFinesUseCase.execute(getUserFinesCommand));

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                getUserFinesCommand.requesterId(),
                getUserFinesCommand.userId(),
                UserRole.MANAGER);
        verify(this.userRetrievalPersistencePort, times(1)).findById(getUserFinesCommand.userId());
        verify(this.fineRetrievalPersistencePort, times(0)).findByUserId(getUserFinesCommand.userId());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test execute when user has no fines then return empty list.
     */
    @Test
    void testExecute_whenUserHasNoFines_thenReturnEmptyList() {

        // Given
        final var getUserFinesCommand = Instancio.create(GetUserFinesCommand.class);
        final var user = Instancio.create(User.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserFinesCommand.requesterId(),
                getUserFinesCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserFinesCommand.userId())).thenReturn(Optional.of(user));
        when(this.fineRetrievalPersistencePort.findByUserId(getUserFinesCommand.userId())).thenReturn(List.of());

        final var result = this.getUserFinesUseCase.execute(getUserFinesCommand);

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).findByUserId(getUserFinesCommand.userId());
        assertEquals(List.of(), result);
    }
}
