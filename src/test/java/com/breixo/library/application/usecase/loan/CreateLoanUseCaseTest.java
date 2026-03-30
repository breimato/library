package com.breixo.library.application.usecase.loan;

import java.util.List;


import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
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
import com.breixo.library.domain.service.LoanPolicyValidationService;
import com.breixo.library.domain.service.ReservationPolicyValidationService;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        /** The loan policy validation service. */
        @Mock
        LoanPolicyValidationService loanPolicyValidationService;

        /** The loan creation persistence port. */
        @Mock
        LoanCreationPersistencePort loanCreationPersistencePort;


        /** The reservation policy validation service. */
        @Mock
        ReservationPolicyValidationService reservationPolicyValidationService;

        /**
         * Test execute when user not found then throw user exception.
         */
        @Test
        void testExecute_whenUserNotFound_thenThrowUserException() {

                // Given
                final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
                final var userException = new UserException(
                                ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                                ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR);

                // When
                when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenThrow(userException);

                // Then
                final var exception = assertThrows(UserException.class,
                                () -> this.createLoanUseCase.execute(createLoanCommand));
                verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanCommand.userId());
                verify(this.bookRetrievalPersistencePort, times(0)).findById(createLoanCommand.bookId());
                assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR, exception.getCode());
                assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
        }

        /**
         * Test execute when book not found then throw book exception.
         */
        @Test
        void testExecute_whenBookNotFound_thenThrowBookException() {

                // Given
                final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
                final var user = Instancio.create(User.class);
                final var bookException = new BookException(
                                ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                                ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
                final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                                .userId(user.id())
                                .build();

                // When
                when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
                when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenThrow(bookException);

                // Then
                final var exception = assertThrows(BookException.class,
                                () -> this.createLoanUseCase.execute(createLoanCommand));
                verify(this.userRetrievalPersistencePort, times(1)).findById(createLoanCommand.userId());
                verify(this.bookRetrievalPersistencePort, times(1)).findById(createLoanCommand.bookId());
                verify(this.loanRetrievalPersistencePort, times(0)).find(loanSearchCriteriaCommand);
                assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR, exception.getCode());
                assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
        }

        /**
         * Test execute when policy rejected then throw loan exception.
         */
        @Test
        void testExecute_whenPolicyRejected_thenThrowLoanException() {

                // Given
                final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
                final var user = Instancio.create(User.class);
                final var book = Instancio.create(Book.class);
                final var loanList = List.of(Instancio.create(Loan.class));
                final var loanException = new LoanException(
                                ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                                ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);

                // When
                when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
                when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenReturn(book);
                when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
                doThrow(loanException).when(this.loanPolicyValidationService).checkCanBorrow(user, book, loanList);

                // Then
                final var exception = assertThrows(LoanException.class,
                                () -> this.createLoanUseCase.execute(createLoanCommand));

                verify(this.loanRetrievalPersistencePort, times(1)).findByUserId(user.id());
                verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(user, book, loanList);
                verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
                assertEquals(ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR, exception.getCode());
                assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, exception.getMessage());
        }

        /**
         * Test execute when user has pending fines then throw loan exception.
         */
        @Test
        void testExecute_whenUserHasPendingFines_thenThrowLoanException() {

                // Given
                final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
                final var user = Instancio.create(User.class);
                final var book = Instancio.create(Book.class);
                final var loanList = List.of(Instancio.create(Loan.class));
                final var loanException = new LoanException(
                                ExceptionMessageConstants.USER_HAS_PENDING_FINES_CODE_ERROR,
                                ExceptionMessageConstants.USER_HAS_PENDING_FINES_MESSAGE_ERROR);

                // When
                when(this.userRetrievalPersistencePort.findById(createLoanCommand.userId())).thenReturn(user);
                when(this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())).thenReturn(book);
                when(this.loanRetrievalPersistencePort.findByUserId(user.id())).thenReturn(loanList);
                doThrow(loanException).when(this.loanPolicyValidationService).checkUserHasNoPendingFines(loanList);

                // Then
                final var exception = assertThrows(LoanException.class,
                                () -> this.createLoanUseCase.execute(createLoanCommand));
                verify(this.loanRetrievalPersistencePort, times(1)).findByUserId(user.id());
                verify(this.loanPolicyValidationService, times(1)).checkUserHasNoPendingFines(loanList);
                verify(this.loanPolicyValidationService, times(0)).checkCanBorrow(user, book, loanList);
                verify(this.reservationPolicyValidationService, times(0)).checkReservationPrecedence(user.id(),
                                book.id());
                verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
                assertEquals(ExceptionMessageConstants.USER_HAS_PENDING_FINES_CODE_ERROR, exception.getCode());
                assertEquals(ExceptionMessageConstants.USER_HAS_PENDING_FINES_MESSAGE_ERROR, exception.getMessage());
        }

        /**
         * Test execute when book reserved by another user then throw loan exception.
         */
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
                                .checkReservationPrecedence(user.id(), book.id());

                // Then
                final var ex = assertThrows(LoanException.class,
                                () -> this.createLoanUseCase.execute(createLoanCommand));
                verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(user, book, loanList);
                verify(this.reservationPolicyValidationService, times(1)).checkReservationPrecedence(user.id(),
                                book.id());
                verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
                assertEquals(ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_CODE_ERROR, ex.getCode());
                assertEquals(ExceptionMessageConstants.LOAN_BOOK_RESERVED_BY_ANOTHER_USER_MESSAGE_ERROR,
                                ex.getMessage());
        }

        /**
         * Test execute when all conditions met then create and return loan.
         */
        @Test
        void testExecute_whenAllConditionsMet_thenCreateAndReturnLoan() {

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
                verify(this.loanRetrievalPersistencePort, times(1)).findByUserId(user.id());
                verify(this.loanPolicyValidationService, times(1)).checkUserHasNoPendingFines(loanList);
                verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(user, book, loanList);
                verify(this.reservationPolicyValidationService, times(1)).checkReservationPrecedence(user.id(),
                                book.id());
                verify(this.loanCreationPersistencePort, times(1)).execute(createLoanCommand);
                assertEquals(loan, result);
        }
}
