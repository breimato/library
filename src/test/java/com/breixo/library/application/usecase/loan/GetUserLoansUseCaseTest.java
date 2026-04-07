package com.breixo.library.application.usecase.loan;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.loan.GetUserLoansCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
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

/** The Class Get User Loans Use Case Test. */
@ExtendWith(MockitoExtension.class)
class GetUserLoansUseCaseTest {

    /** The get user loans use case. */
    @InjectMocks
    GetUserLoansUseCaseImpl getUserLoansUseCase;

    /** The authorization service. */
    @Mock
    AuthorizationService authorizationService;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /**
     * Test execute when user exists then return loans.
     */
    @Test
    void testExecute_whenUserExists_thenReturnLoans() {

        // Given
        final var getUserLoansCommand = Instancio.create(GetUserLoansCommand.class);
        final var user = Instancio.create(User.class);
        final var loans = Instancio.createList(Loan.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserLoansCommand.requesterId(),
                getUserLoansCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserLoansCommand.userId())).thenReturn(Optional.of(user));
        when(this.loanRetrievalPersistencePort.findByUserId(getUserLoansCommand.userId())).thenReturn(loans);

        final var result = this.getUserLoansUseCase.execute(getUserLoansCommand);

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                getUserLoansCommand.requesterId(),
                getUserLoansCommand.userId(),
                UserRole.MANAGER);
        verify(this.userRetrievalPersistencePort, times(1)).findById(getUserLoansCommand.userId());
        verify(this.loanRetrievalPersistencePort, times(1)).findByUserId(getUserLoansCommand.userId());
        assertEquals(loans, result);
    }

    /**
     * Test execute when user not found then throw user exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {

        // Given
        final var getUserLoansCommand = Instancio.create(GetUserLoansCommand.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserLoansCommand.requesterId(),
                getUserLoansCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserLoansCommand.userId())).thenReturn(Optional.empty());

        final var exception = assertThrows(UserException.class,
                () -> this.getUserLoansUseCase.execute(getUserLoansCommand));

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                getUserLoansCommand.requesterId(),
                getUserLoansCommand.userId(),
                UserRole.MANAGER);
        verify(this.userRetrievalPersistencePort, times(1)).findById(getUserLoansCommand.userId());
        verify(this.loanRetrievalPersistencePort, times(0)).findByUserId(getUserLoansCommand.userId());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test execute when user has no loans then return empty list.
     */
    @Test
    void testExecute_whenUserHasNoLoans_thenReturnEmptyList() {

        // Given
        final var getUserLoansCommand = Instancio.create(GetUserLoansCommand.class);
        final var user = Instancio.create(User.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                getUserLoansCommand.requesterId(),
                getUserLoansCommand.userId(),
                UserRole.MANAGER);
        when(this.userRetrievalPersistencePort.findById(getUserLoansCommand.userId())).thenReturn(Optional.of(user));
        when(this.loanRetrievalPersistencePort.findByUserId(getUserLoansCommand.userId())).thenReturn(List.of());

        final var result = this.getUserLoansUseCase.execute(getUserLoansCommand);

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).findByUserId(getUserLoansCommand.userId());
        assertEquals(List.of(), result);
    }
}
