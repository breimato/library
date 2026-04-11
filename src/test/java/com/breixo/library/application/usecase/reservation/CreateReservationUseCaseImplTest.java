package com.breixo.library.application.usecase.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.ReservationException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.reservation.ReservationCreationPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.port.input.book.BookPolicyValidationService;
import com.breixo.library.domain.port.input.reservation.ReservationPolicyValidationService;
import com.breixo.library.domain.port.input.user.UserPolicyValidationService;

/** The Class Create Reservation Use Case Impl Test. */
@ExtendWith(MockitoExtension.class)
class CreateReservationUseCaseImplTest {

    /** The create reservation use case impl. */
    @InjectMocks
    CreateReservationUseCaseImpl createReservationUseCaseImpl;

    /** The user retrieval persistence port. */
    @Mock
    UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The user policy validation service. */
    @Mock
    UserPolicyValidationService userPolicyValidationService;

    /** The book policy validation service. */
    @Mock
    BookPolicyValidationService bookPolicyValidationService;

    /** The reservation policy validation service. */
    @Mock
    ReservationPolicyValidationService reservationPolicyValidationService;

    /** The reservation creation persistence port. */
    @Mock
    ReservationCreationPersistencePort reservationCreationPersistencePort;

    /**
     * Test execute when user not found then throw user exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);

        // When
        when(this.userRetrievalPersistencePort.findById(createReservationCommand.userId())).thenReturn(Optional.empty());
        final var userException = assertThrows(UserException.class,
                () -> this.createReservationUseCaseImpl.execute(createReservationCommand));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).findById(createReservationCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(0)).findById(createReservationCommand.bookId());
        verify(this.loanRetrievalPersistencePort, times(0)).findByUserId(createReservationCommand.userId());
        verify(this.reservationCreationPersistencePort, times(0)).execute(createReservationCommand);
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, userException.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, userException.getMessage());
    }

    /**
     * Test execute when book not found then throw book exception.
     */
    @Test
    void testExecute_whenBookNotFound_thenThrowBookException() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var user = Instancio.create(User.class);

        // When
        when(this.userRetrievalPersistencePort.findById(createReservationCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createReservationCommand.bookId())).thenReturn(Optional.empty());
        final var bookException = assertThrows(BookException.class,
                () -> this.createReservationUseCaseImpl.execute(createReservationCommand));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).findById(createReservationCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(1)).findById(createReservationCommand.bookId());
        verify(this.loanRetrievalPersistencePort, times(0)).findByUserId(user.id());
        verify(this.reservationCreationPersistencePort, times(0)).execute(createReservationCommand);
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR, bookException.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, bookException.getMessage());
    }

    /** Test execute when user policy rejected then throw user exception. */
    @Test
    void testExecute_whenUserPolicyRejected_thenThrowUserException() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var userException = new UserException(
                ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createReservationCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createReservationCommand.bookId())).thenReturn(Optional.of(book));
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        doThrow(userException).when(this.userPolicyValidationService).check(user, loanList);
        final var exception = assertThrows(UserException.class,
                () -> this.createReservationUseCaseImpl.execute(createReservationCommand));

        // Then
        verify(this.userPolicyValidationService, times(1)).check(user, loanList);
        verify(this.bookPolicyValidationService, times(0)).checkIsReservable(book);
        verify(this.reservationCreationPersistencePort, times(0)).execute(createReservationCommand);
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when book not reservable then throw book exception. */
    @Test
    void testExecute_whenBookNotReservable_thenThrowBookException() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var bookException = new BookException(
                ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_CODE_ERROR,
                ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createReservationCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createReservationCommand.bookId())).thenReturn(Optional.of(book));
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        doThrow(bookException).when(this.bookPolicyValidationService).checkIsReservable(book);
        final var exception = assertThrows(BookException.class,
                () -> this.createReservationUseCaseImpl.execute(createReservationCommand));

        // Then
        verify(this.bookPolicyValidationService, times(1)).checkIsReservable(book);
        verify(this.reservationPolicyValidationService, times(0)).checkNoActiveReservation(user.id(), book.id());
        verify(this.reservationCreationPersistencePort, times(0)).execute(createReservationCommand);
        assertEquals(ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.RESERVATION_COPIES_AVAILABLE_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when user already has active reservation then throw reservation exception. */
    @Test
    void testExecute_whenUserAlreadyHasActiveReservation_thenThrowReservationException() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var reservationException = new ReservationException(
                ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_CODE_ERROR,
                ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createReservationCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createReservationCommand.bookId())).thenReturn(Optional.of(book));
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        doThrow(reservationException).when(this.reservationPolicyValidationService)
                .checkNoActiveReservation(user.id(), book.id());
        final var exception = assertThrows(ReservationException.class,
                () -> this.createReservationUseCaseImpl.execute(createReservationCommand));

        // Then
        verify(this.reservationPolicyValidationService, times(1)).checkNoActiveReservation(user.id(), book.id());
        verify(this.reservationCreationPersistencePort, times(0)).execute(createReservationCommand);
        assertEquals(ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.RESERVATION_ALREADY_EXISTS_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when all conditions met then return reservation. */
    @Test
    void testExecute_whenAllConditionsMet_thenReturnReservation() {

        // Given
        final var createReservationCommand = Instancio.create(CreateReservationCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var reservation = Instancio.create(Reservation.class);

        // When
        when(this.userRetrievalPersistencePort.findById(createReservationCommand.userId())).thenReturn(Optional.of(user));
        when(this.bookRetrievalPersistencePort.findById(createReservationCommand.bookId())).thenReturn(Optional.of(book));
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        when(this.reservationCreationPersistencePort.execute(createReservationCommand)).thenReturn(reservation);
        final var result = this.createReservationUseCaseImpl.execute(createReservationCommand);

        // Then
        verify(this.userPolicyValidationService, times(1)).check(user, loanList);
        verify(this.bookPolicyValidationService, times(1)).checkIsReservable(book);
        verify(this.reservationPolicyValidationService, times(1)).checkNoActiveReservation(user.id(), book.id());
        verify(this.reservationCreationPersistencePort, times(1)).execute(createReservationCommand);
        assertEquals(reservation, result);
    }
}
