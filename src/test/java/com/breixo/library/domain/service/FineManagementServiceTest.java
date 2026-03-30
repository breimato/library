package com.breixo.library.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.breixo.library.domain.command.fine.CreateFineCommand;
import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.output.fine.FineCreationPersistencePort;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineUpdatePersistencePort;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Fine Management Service Test. */
@ExtendWith(MockitoExtension.class)
class FineManagementServiceTest {

    /** The fine management service. */
    @InjectMocks
    FineManagementServiceImpl fineManagementService;

    /** The fine calculation service. */
    @Mock
    FineCalculationService fineCalculationService;

    /** The fine retrieval persistence port. */
    @Mock
    FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The fine creation persistence port. */
    @Mock
    FineCreationPersistencePort fineCreationPersistencePort;

    /** The fine update persistence port. */
    @Mock
    FineUpdatePersistencePort fineUpdatePersistencePort;

    /**
     * Test execute when amount is zero then do nothing.
     */
    @Test
    void testExecute_whenAmountIsZero_thenDoNothing() {

        // Given
        final var loanId = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var dueDate = Instancio.create(LocalDate.class);
        final var returnDate = Instancio.create(LocalDate.class);
        final var loan = Loan.builder()
                .id(loanId)
                .userId(userId)
                .bookId(bookId)
                .dueDate(dueDate)
                .returnDate(null)
                .status(LoanStatus.ACTIVE)
                .build();

        // When
        when(this.fineCalculationService.execute(loan.dueDate(), returnDate)).thenReturn(BigDecimal.ZERO);
        this.fineManagementService.execute(loan, returnDate);

        // Then
        verify(this.fineCalculationService, times(1)).execute(loan.dueDate(), returnDate);
        verify(this.fineRetrievalPersistencePort, times(0)).find(FineSearchCriteriaCommand.builder().loanId(loan.id()).build());
        assertTrue(Boolean.TRUE);
    }

    /**
     * Test execute when overdue and no fine then create fine.
     */
    @Test
    void testExecute_whenOverdueAndNoFine_thenCreateFine() {

        // Given
        final var loanId = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var dueDate = Instancio.create(LocalDate.class);
        final var returnDate = Instancio.create(LocalDate.class);
        final var amountEuros = Instancio.create(BigDecimal.class).abs().add(BigDecimal.ONE);
        final var loan = Loan.builder()
                .id(loanId)
                .userId(userId)
                .bookId(bookId)
                .dueDate(dueDate)
                .returnDate(null)
                .status(LoanStatus.OVERDUE)
                .build();
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .loanId(loan.id())
                .build();
        final var createFineCommand = CreateFineCommand.builder()
                .loanId(loan.id())
                .amountEuros(amountEuros)
                .statusId(FineStatus.PENDING.getId())
                .build();

        // When
        when(this.fineCalculationService.execute(loan.dueDate(), returnDate)).thenReturn(amountEuros);
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(List.of());
        this.fineManagementService.execute(loan, returnDate);

        // Then
        verify(this.fineCalculationService, times(1)).execute(loan.dueDate(), returnDate);
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineCreationPersistencePort, times(1)).execute(createFineCommand);
        assertTrue(Boolean.TRUE);
    }

    /**
     * Test execute when overdue and pending fine then update fine.
     */
    @Test
    void testExecute_whenOverdueAndPendingFine_thenUpdateFine() {

        // Given
        final var loanId = Instancio.create(Integer.class);
        final var userId = Instancio.create(Integer.class);
        final var bookId = Instancio.create(Integer.class);
        final var dueDate = Instancio.create(LocalDate.class);
        final var returnDate = Instancio.create(LocalDate.class);
        final var amountEuros = Instancio.create(BigDecimal.class).abs().add(BigDecimal.ONE);
        final var fineId = Instancio.create(Integer.class);
        final var previousAmountEuros = Instancio.create(BigDecimal.class).abs().add(BigDecimal.ONE);
        final var loan = Loan.builder()
                .id(loanId)
                .userId(userId)
                .bookId(bookId)
                .dueDate(dueDate)
                .returnDate(null)
                .status(LoanStatus.OVERDUE)
                .build();
        final var fine = Fine.builder()
                .id(fineId)
                .loanId(loan.id())
                .amountEuros(previousAmountEuros)
                .status(FineStatus.PENDING)
                .paidAt(null)
                .build();
        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .loanId(loan.id())
                .build();
        final var updateFineCommand = UpdateFineCommand.builder()
                .id(fine.id())
                .amountEuros(amountEuros)
                .status(FineStatus.PENDING)
                .build();

        // When
        when(this.fineCalculationService.execute(loan.dueDate(), returnDate)).thenReturn(amountEuros);
        when(this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand)).thenReturn(List.of(fine));
        this.fineManagementService.execute(loan, returnDate);

        // Then
        verify(this.fineCalculationService, times(1)).execute(loan.dueDate(), returnDate);
        verify(this.fineRetrievalPersistencePort, times(1)).find(fineSearchCriteriaCommand);
        verify(this.fineUpdatePersistencePort, times(1)).execute(updateFineCommand);
        assertTrue(Boolean.TRUE);
    }
}
