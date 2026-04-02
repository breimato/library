package com.breixo.library.application.usecase.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.user.User;
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
        when(this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId())).thenReturn(book);
        when(this.loanRequestCreationPersistencePort.execute(createLoanRequestCommand)).thenReturn(loanRequest);
        final var result = this.createLoanRequestUseCase.execute(createLoanRequestCommand);

        // Then
        assertEquals(loanRequest, result);
    }

    /**
     * Test execute when user does not exist then throw user exception.
     */
    @Test
    void testExecute_whenUserDoesNotExist_thenThrowUserException() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);

        // When
        when(this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId()))
                .thenThrow(new UserException("LIB-USER-001", "Error: User not found"));

        // Then
        assertThrows(UserException.class, () -> this.createLoanRequestUseCase.execute(createLoanRequestCommand));
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
        when(this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId())).thenReturn(user);
        when(this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId()))
                .thenThrow(new BookException("LIB-BOOK-001", "Error: Book not found"));

        // Then
        assertThrows(BookException.class, () -> this.createLoanRequestUseCase.execute(createLoanRequestCommand));
    }
}
