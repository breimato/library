package com.breixo.library.application.usecase.loanrequest;

import java.util.Optional;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestPolicyValidationService;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestCreationPersistencePort;
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

/** The Class Create Loan Request Use Case Test. */
@ExtendWith(MockitoExtension.class)
class CreateLoanRequestUseCaseTest {

    /** The create loan request use case. */
    @InjectMocks
    CreateLoanRequestUseCaseImpl createLoanRequestUseCase;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The loan request creation persistence port. */
    @Mock
    LoanRequestCreationPersistencePort loanRequestCreationPersistencePort;

    /** The loan request policy validation service. */
    @Mock
    LoanRequestPolicyValidationService loanRequestPolicyValidationService;

    /** The authorization service. */
    @Mock
    AuthorizationService authorizationService;

    /**
     * Test execute when user and book exist then create and return loan request.
     */
    @Test
    void testExecute_whenUserAndBookExist_thenCreateAndReturnLoanRequest() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanRequest = Instancio.create(LoanRequest.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);
        doNothing().when(this.loanRequestPolicyValidationService).validateCreation(createLoanRequestCommand);
        when(this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId())).thenReturn(Optional.of(book));
        when(this.loanRequestCreationPersistencePort.execute(createLoanRequestCommand)).thenReturn(loanRequest);
        
        final var actualLoanRequest = this.createLoanRequestUseCase.execute(createLoanRequestCommand);

        // Then
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);
        verify(this.loanRequestPolicyValidationService, times(1)).validateCreation(createLoanRequestCommand);
        verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.bookId());
        verify(this.loanRequestCreationPersistencePort, times(1)).execute(createLoanRequestCommand);
        assertEquals(loanRequest, actualLoanRequest);
    }

    /**
     * Test execute when user does not exist then throw user exception.
     */
    @Test
    void testExecute_whenUserDoesNotExist_thenThrowUserException() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);
        doNothing().when(this.loanRequestPolicyValidationService).validateCreation(createLoanRequestCommand);
        when(this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId()))
                .thenReturn(Optional.empty());

        // Then
        final var userException = assertThrows(UserException.class,
                () -> this.createLoanRequestUseCase.execute(createLoanRequestCommand));
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, userException.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, userException.getMessage());
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);
        verify(this.loanRequestPolicyValidationService, times(1)).validateCreation(createLoanRequestCommand);
        verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(0)).findById(createLoanRequestCommand.bookId());
        verify(this.loanRequestCreationPersistencePort, times(0)).execute(createLoanRequestCommand);
    }

    /**
     * Test execute when book does not exist then throw book exception.
     */
    @Test
    void testExecute_whenBookDoesNotExist_thenThrowBookException() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var user = Instancio.create(User.class);

        // When
        doNothing().when(this.authorizationService).requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);
        doNothing().when(this.loanRequestPolicyValidationService).validateCreation(createLoanRequestCommand);
        when(this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId()))
                .thenReturn(Optional.empty());

        // Then
        final var bookException = assertThrows(BookException.class,
                () -> this.createLoanRequestUseCase.execute(createLoanRequestCommand));
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR, bookException.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, bookException.getMessage());
        verify(this.authorizationService, times(1)).requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);
        verify(this.loanRequestPolicyValidationService, times(1)).validateCreation(createLoanRequestCommand);
        verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.bookId());
        verify(this.loanRequestCreationPersistencePort, times(0)).execute(createLoanRequestCommand);
    }
}
