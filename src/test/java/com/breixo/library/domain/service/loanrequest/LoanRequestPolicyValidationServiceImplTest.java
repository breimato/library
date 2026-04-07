package com.breixo.library.domain.service.loanrequest;

import java.util.Collections;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The Class Loan Request Policy Validation Service Impl Test.
 */
@ExtendWith(MockitoExtension.class)
class LoanRequestPolicyValidationServiceImplTest {

    /** The loan request policy validation service. */
    @InjectMocks
    LoanRequestPolicyValidationServiceImpl loanRequestPolicyValidationService;

    /** The fine retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The loan retrieval persistence port. */
    @Mock
    LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    @Mock
    BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /**
     * Test validate creation when valid then success.
     */
    @Test
    void testValidateCreation_whenValid_thenSuccess() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var book = Instancio.of(Book.class)
                .set(org.instancio.Select.field(Book::availableCopies), 0)
                .create();

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(FineStatus.PENDING.getId())
                .build();
        
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(LoanStatus.ACTIVE.getId())
                .build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(Collections.emptyList());
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(Collections.emptyList());
        when(this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId())).thenReturn(book);

        // Then
        assertDoesNotThrow(() -> this.loanRequestPolicyValidationService.validateCreation(createLoanRequestCommand));
        
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.bookRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.bookId());
    }

    /**
     * Test validate creation when user has pending fines then throw exception.
     */
    @Test
    void testValidateCreation_whenUserHasPendingFines_thenThrowException() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var fineList = Instancio.ofList(Fine.class).size(1).create();

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(FineStatus.PENDING.getId())
                .build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(fineList);

        final var ex = assertThrows(LoanRequestException.class, 
                () -> this.loanRequestPolicyValidationService.validateCreation(createLoanRequestCommand));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.loanRetrievalPersistencePort, times(0)).find(LoanSearchCriteriaCommand.builder().build());
        verify(this.bookRetrievalPersistencePort, times(0)).findById(createLoanRequestCommand.bookId());
        
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_USER_HAS_PENDING_FINES_CODE_ERROR, ex.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_USER_HAS_PENDING_FINES_MESSAGE_ERROR, ex.getMessage());
    }

    /**
     * Test validate creation when user has max active loans then throw exception.
     */
    @Test
    void testValidateCreation_whenUserHasMaxActiveLoans_thenThrowException() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var loanList = Instancio.ofList(Loan.class).size(3).create();

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(FineStatus.PENDING.getId())
                .build();
                
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(LoanStatus.ACTIVE.getId())
                .build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(Collections.emptyList());
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(loanList);

        final var ex = assertThrows(LoanRequestException.class, 
                () -> this.loanRequestPolicyValidationService.validateCreation(createLoanRequestCommand));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.bookRetrievalPersistencePort, times(0)).findById(createLoanRequestCommand.bookId());
        
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR, ex.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR, ex.getMessage());
    }

    /**
     * Test validate creation when book has available copies then throw exception.
     */
    @Test
    void testValidateCreation_whenBookHasAvailableCopies_thenThrowException() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var book = Instancio.of(Book.class)
                .set(org.instancio.Select.field(Book::availableCopies), 1)
                .create();

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(FineStatus.PENDING.getId())
                .build();
                
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(createLoanRequestCommand.userId())
                .statusId(LoanStatus.ACTIVE.getId())
                .build();

        // When
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(Collections.emptyList());
        when(this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)).thenReturn(Collections.emptyList());
        when(this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId())).thenReturn(book);

        final var ex = assertThrows(LoanRequestException.class, 
                () -> this.loanRequestPolicyValidationService.validateCreation(createLoanRequestCommand));

        // Then
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.loanRetrievalPersistencePort, times(1)).find(loanSearchCriteriaCommand);
        verify(this.bookRetrievalPersistencePort, times(1)).findById(createLoanRequestCommand.bookId());
        
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_BOOK_AVAILABLE_CODE_ERROR, ex.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_BOOK_AVAILABLE_MESSAGE_ERROR, ex.getMessage());
    }
}
