package com.breixo.library.application.usecase.reservation;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.reservation.GetUserReservationsCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.reservation.ReservationRetrievalPersistencePort;
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

/** The Class Get User Reservations Use Case Test. */
@ExtendWith(MockitoExtension.class)
class GetUserReservationsUseCaseTest {

    /** The get user reservations use case. */
    @InjectMocks
    GetUserReservationsUseCaseImpl getUserReservationsUseCase;

    /** The authorization service. */
    @Mock
    AuthorizationService authorizationService;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The reservation retrieval persistence port. */
    @Mock
    ReservationRetrievalPersistencePort reservationRetrievalPersistencePort;

    /**
     * Test execute when user exists then return reservations.
     */
    @Test
    void testExecute_whenUserExists_thenReturnReservations() {

        // Given
        final var getUserReservationsCommand = Instancio.create(GetUserReservationsCommand.class);
        final var user = Instancio.create(User.class);
        final var reservations = Instancio.createList(Reservation.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserReservationsCommand.requesterId(),
                getUserReservationsCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserReservationsCommand.userId())).thenReturn(Optional.of(user));
        when(this.reservationRetrievalPersistencePort.findByUserId(getUserReservationsCommand.userId())).thenReturn(reservations);

        final var result = this.getUserReservationsUseCase.execute(getUserReservationsCommand);

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                getUserReservationsCommand.requesterId(),
                getUserReservationsCommand.userId(),
                UserRole.MANAGER);
        verify(this.userRetrievalPersistencePort, times(1)).findById(getUserReservationsCommand.userId());
        verify(this.reservationRetrievalPersistencePort, times(1)).findByUserId(getUserReservationsCommand.userId());
        assertEquals(reservations, result);
    }

    /**
     * Test execute when user not found then throw user exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {

        // Given
        final var getUserReservationsCommand = Instancio.create(GetUserReservationsCommand.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserReservationsCommand.requesterId(),
                getUserReservationsCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserReservationsCommand.userId())).thenReturn(Optional.empty());

        final var exception = assertThrows(UserException.class,
                () -> this.getUserReservationsUseCase.execute(getUserReservationsCommand));

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                getUserReservationsCommand.requesterId(),
                getUserReservationsCommand.userId(),
                UserRole.MANAGER);
        verify(this.userRetrievalPersistencePort, times(1)).findById(getUserReservationsCommand.userId());
        verify(this.reservationRetrievalPersistencePort, times(0)).findByUserId(getUserReservationsCommand.userId());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test execute when user has no reservations then return empty list.
     */
    @Test
    void testExecute_whenUserHasNoReservations_thenReturnEmptyList() {

        // Given
        final var getUserReservationsCommand = Instancio.create(GetUserReservationsCommand.class);
        final var user = Instancio.create(User.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserReservationsCommand.requesterId(),
                getUserReservationsCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserReservationsCommand.userId())).thenReturn(Optional.of(user));
        when(this.reservationRetrievalPersistencePort.findByUserId(getUserReservationsCommand.userId())).thenReturn(List.of());

        final var result = this.getUserReservationsUseCase.execute(getUserReservationsCommand);

        // Then
        verify(this.reservationRetrievalPersistencePort, times(1)).findByUserId(getUserReservationsCommand.userId());
        assertEquals(List.of(), result);
    }
}
