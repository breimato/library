package com.breixo.library.domain.service;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.breixo.library.domain.service.user.UserPolicyValidationServiceImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;

/** The Class User Policy Validation Service Impl Test. */
@ExtendWith(MockitoExtension.class)
class UserPolicyValidationServiceImplTest {

    /** The fine retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    @InjectMocks
    UserPolicyValidationServiceImpl userPolicyValidationServiceImpl;

    /** Test check when user blocked then throw user exception. */
    @Test
    void testCheck_whenUserBlocked_thenThrowUserException() {

        // Given
        final var user = Instancio.of(User.class)
                .set(field(User.class, "status"), UserStatus.BLOCKED)
                .create();
        final var loanList = List.<Loan>of();

        // When/Then
        final var exception = assertThrows(UserException.class,
                () -> this.userPolicyValidationServiceImpl.check(user, loanList));
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check when user suspended then throw user exception. */
    @Test
    void testCheck_whenUserSuspended_thenThrowUserException() {

        // Given
        final var user = Instancio.of(User.class)
                .set(field(User.class, "status"), UserStatus.SUSPENDED)
                .create();
        final var loanList = List.<Loan>of();

        // When/Then
        final var exception = assertThrows(UserException.class,
                () -> this.userPolicyValidationServiceImpl.check(user, loanList));
        assertEquals(ExceptionMessageConstants.USER_SUSPENDED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_SUSPENDED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check when pending fines exist then throw user exception. */
    @Test
    void testCheck_whenPendingFinesExist_thenThrowUserException() {

        // Given
        final var user = Instancio.of(User.class)
                .set(field(User.class, "status"), UserStatus.ACTIVE)
                .create();
        final var loan = Instancio.create(Loan.class);
        final var fine = Instancio.create(Fine.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .loanId(loan.id())
                .statusId(FineStatus.PENDING.getId())
                .build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(List.of(fine));
        final var exception = assertThrows(UserException.class,
                () -> this.userPolicyValidationServiceImpl.check(user, List.of(loan)));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        assertEquals(ExceptionMessageConstants.USER_HAS_PENDING_FINES_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_HAS_PENDING_FINES_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test check when active user and no pending fines then does not throw. */
    @Test
    void testCheck_whenActiveUserAndNoPendingFines_thenDoesNotThrow() {

        // Given
        final var user = Instancio.of(User.class)
                .set(field(User.class, "status"), UserStatus.ACTIVE)
                .create();
        final var loan = Instancio.create(Loan.class);
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .loanId(loan.id())
                .statusId(FineStatus.PENDING.getId())
                .build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(List.of());
        assertDoesNotThrow(() -> this.userPolicyValidationServiceImpl.check(user, List.of(loan)));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
    }
}
