package com.breixo.library.application.usecase.loan;

import java.util.List;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
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

    /**
     * Test execute when user not found then throw user exception.
     */
    @Test
    void testExecute_whenUserNotFound_thenThrowUserException() {
        
        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .id(createLoanCommand.userId())
                .build();
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(createLoanCommand.bookId())
                .build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of());
        final var userException = assertThrows(UserException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.bookRetrievalPersistencePort, times(0)).find(bookSearchCriteriaCommand);
        assertEquals(ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR, userException.getMessage());
    }

    /**
     * Test execute when book not found then throw book exception.
     */
    @Test
    void testExecute_whenBookNotFound_thenThrowBookException() {
        
        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var user = Instancio.create(User.class);
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .id(createLoanCommand.userId())
                .build();
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(createLoanCommand.bookId())
                .build();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(user.id())
                .build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of(user));
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(List.of());
        final var bookException = assertThrows(BookException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.userRetrievalPersistencePort, times(1)).find(userSearchCriteriaCommand);
        verify(this.bookRetrievalPersistencePort, times(1)).find(bookSearchCriteriaCommand);
        verify(this.loanRetrievalPersistencePort, times(0)).find(loanSearchCriteriaCommand);
        assertEquals(ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR, bookException.getMessage());
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
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .id(createLoanCommand.userId())
                .build();
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(createLoanCommand.bookId())
                .build();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(user.id())
                .build();
        final var loanException = new LoanException(
                ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of(user));
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(List.of(book));
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loanList);
        doThrow(loanException).when(this.loanPolicyValidationService).checkCanBorrow(user, book, loanList);
        final var thrownLoanException = assertThrows(LoanException.class,
                () -> this.createLoanUseCase.execute(createLoanCommand));

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(user, book, loanList);
        verify(this.loanCreationPersistencePort, times(0)).execute(createLoanCommand);
        assertEquals(ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR, thrownLoanException.getMessage());
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
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .id(createLoanCommand.userId())
                .build();
        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(createLoanCommand.bookId())
                .build();
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(user.id())
                .build();

        // When
        when(this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)).thenReturn(List.of(user));
        when(this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)).thenReturn(List.of(book));
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loanList);
        when(this.loanCreationPersistencePort.execute(createLoanCommand)).thenReturn(loan);
        final var result = this.createLoanUseCase.execute(createLoanCommand);

        // Then
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanPolicyValidationService, times(1)).checkCanBorrow(user, book, loanList);
        verify(this.loanCreationPersistencePort, times(1)).execute(createLoanCommand);
        assertEquals(loan, result);
    }
}
