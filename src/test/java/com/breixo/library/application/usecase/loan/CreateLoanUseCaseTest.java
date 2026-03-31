package com.breixo.library.application.usecase.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.event.LoanCreatedDomainEvent;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanCreationPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.port.input.book.BookPolicyValidationService;
import com.breixo.library.domain.port.input.loan.LoanPolicyValidationService;
import com.breixo.library.domain.port.input.reservation.ReservationPolicyValidationService;
import com.breixo.library.domain.port.input.user.UserPolicyValidationService;

/** The Class Create Loan Use Case Test. */
@ExtendWith(MockitoExtension.class)
class CreateLoanUseCaseTest {

    /** The create loan use case. */
    @InjectMocks
    CreateLoanUseCaseImpl createLoanUseCase;

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

    /** The loan policy validation service. */
    @Mock
    LoanPolicyValidationService loanPolicyValidationService;

    /** The reservation policy validation service. */
    @Mock
    ReservationPolicyValidationService reservationPolicyValidationService;

    /** The loan creation persistence port. */
    @Mock
    LoanCreationPersistencePort loanCreationPersistencePort;

    /** The application event publisher. */
    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    /** Test execute when user not found then throw user exception. */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var userException = new UserException(
                ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenThrow(userException);
        final var exception = assertThrows(UserException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(0)).findById(createLoanCommand.bookId());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when book not found then throw book exception. */
    @Test
    void testExecute_whenBookNotFound_thenThrowBookException() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var user = Instancio.create(User.class);
        final var bookException = new BookException(
                ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenThrow(bookException);
        final var exception = assertThrows(BookException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanCommand.userId());
        verify(this.bookRetrievalPersistencePort, times(1)).findById(createLoanCommand.bookId());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when user policy rejected then throw user exception. */
    @Test
    void testExecute_whenUserPolicyRejected_thenThrowUserException() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var userException = new UserException(
                ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenReturn(book);
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        doThrow(userException).when(this.userPolicyValidationService).check(user, loanList);
        final var exception = assertThrows(UserException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.userPolicyValidationService, times(1)).check(user, loanList);
        verify(this.bookPolicyValidationService, times(0)).checkIsBorrowable(book);
        verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when book not borrowable then throw book exception. */
    @Test
    void testExecute_whenBookNotBorrowable_thenThrowBookException() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var bookException = new BookException(
                ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR,
                ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenReturn(book);
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        doThrow(bookException).when(this.bookPolicyValidationService).checkIsBorrowable(book);
        final var exception = assertThrows(BookException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.bookPolicyValidationService, times(1)).checkIsBorrowable(book);
        verify(this.loanPolicyValidationService, times(0)).checkCanBorrow(book, loanList);
        verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.BOOK_RETIRED_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when book reserved by another user then throw loan exception. */
    @Test
    void testExecute_whenBookReservedByAnotherUser_thenThrowLoanException() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var loanException = new LoanException(
                ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR,
                ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenReturn(book);
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        doThrow(loanException).when(this.reservationPolicyValidationService)
                .checkPrecedence(user.id(), book.id());
        final var exception = assertThrows(LoanException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(book, loanList);
        verify(this.reservationPolicyValidationService, times(1)).checkPrecedence(user.id(), book.id());
        verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
        assertEquals(ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR, exception.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR, exception.getMessage());
    }

    /** Test execute when all conditions met then create loan and publish event. */
    @Test
    void testExecute_whenAllConditionsMet_thenCreateLoanAndPublishEvent() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var user = Instancio.create(User.class);
        final var book = Instancio.create(Book.class);
        final var loanList = List.of(Instancio.create(Loan.class));
        final var loan = Instancio.create(Loan.class);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenReturn(book);
        when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
        when(this.loanCreationPersistencePort.execute(createLoanCommand)).thenReturn(loan);
        final var result = this.createLoanUseCase.execute(createLoanCommand);

        // Then
        verify(this.userPolicyValidationService, times(1)).check(user, loanList);
        verify(this.bookPolicyValidationService, times(1)).checkIsBorrowable(book);
        verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(book, loanList);
        verify(this.reservationPolicyValidationService, times(1)).checkPrecedence(user.id(), book.id());
        verify(this.loanCreationPersistencePort, times(1)).execute(createLoanCommand);

        final var eventCaptor = ArgumentCaptor.forClass(LoanCreatedDomainEvent.class);
        verify(this.applicationEventPublisher, times(1)).publishEvent(eventCaptor.capture());
        final var capturedEvent = eventCaptor.getValue();
        assertEquals(user.id(), capturedEvent.userId());
        assertEquals(book.id(), capturedEvent.bookId());
        assertEquals(loan.id(), capturedEvent.loanId());
        assertEquals(loan, result);
    }
}
