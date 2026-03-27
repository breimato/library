package com.breixo.library.infrastructure.adapter.output.mapper;

import java.util.List;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.infrastructure.adapter.output.entities.LoanEntity;
import com.breixo.library.infrastructure.mapper.LoanStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Entity Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanEntityMapperTest {

    /** The loan entity mapper. */
    @InjectMocks
    LoanEntityMapperImpl loanEntityMapper;

    /** The loan status mapper. */
    @Mock
    LoanStatusMapper loanStatusMapper;

    /**
     * Test to loan when loan entity is valid then return mapped loan.
     */
    @Test
    void testToLoan_whenLoanEntityIsValid_thenReturnMappedLoan() {
        // Given
        final var loanEntity = Instancio.create(LoanEntity.class);
        final var loanStatus = Instancio.create(LoanStatus.class);

        // When
        when(this.loanStatusMapper.toLoanStatus(loanEntity.getStatusId())).thenReturn(loanStatus);
        final var loan = this.loanEntityMapper.toLoan(loanEntity);

        // Then
        verify(this.loanStatusMapper, times(1)).toLoanStatus(loanEntity.getStatusId());
        assertNotNull(loan);
        assertEquals(loanEntity.getId(), loan.id());
        assertEquals(loanEntity.getUserId(), loan.userId());
        assertEquals(loanEntity.getBookId(), loan.bookId());
        assertEquals(loanEntity.getDueDate(), loan.dueDate());
        assertEquals(loanEntity.getReturnDate(), loan.returnDate());
        assertEquals(loanStatus, loan.status());
        assertEquals(loanEntity.getCreatedAt(), loan.createdAt());
        assertEquals(loanEntity.getUpdatedAt(), loan.updatedAt());
    }

    /**
     * Test to loan when loan entity is null then return null.
     */
    @Test
    void testToLoan_whenLoanEntityIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanEntityMapper.toLoan(null));
    }

    /**
     * Test to loan entity when create loan command is valid then return mapped loan entity.
     */
    @Test
    void testToLoanEntity_whenCreateLoanCommandIsValid_thenReturnMappedLoanEntity() {
        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);

        // When
        final var loanEntity = this.loanEntityMapper.toLoanEntity(createLoanCommand);

        // Then
        assertNotNull(loanEntity);
        assertEquals(createLoanCommand.userId(), loanEntity.getUserId());
        assertEquals(createLoanCommand.bookId(), loanEntity.getBookId());
        assertEquals(createLoanCommand.dueDate(), loanEntity.getDueDate());
    }

    /**
     * Test to loan entity when create loan command is null then return null.
     */
    @Test
    void testToLoanEntity_whenCreateLoanCommandIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanEntityMapper.toLoanEntity(null));
    }

    /**
     * Test to loan list when loan entities are valid then return mapped loan list.
     */
    @Test
    void testToLoanList_whenLoanEntitiesAreValid_thenReturnMappedLoanList() {
        // Given
        final var loanEntity = Instancio.create(LoanEntity.class);
        final var loanEntities = List.of(loanEntity);
        final var loanStatus = Instancio.create(LoanStatus.class);

        // When
        when(this.loanStatusMapper.toLoanStatus(loanEntity.getStatusId())).thenReturn(loanStatus);
        final var loans = this.loanEntityMapper.toLoanList(loanEntities);

        // Then
        assertNotNull(loans);
        assertEquals(1, loans.size());
        assertEquals(loanEntity.getId(), loans.getFirst().id());
    }

    /**
     * Test to loan list when loan entities list is empty then return empty list.
     */
    @Test
    void testToLoanList_whenLoanEntitiesListIsEmpty_thenReturnEmptyList() {
        // When
        final var loans = this.loanEntityMapper.toLoanList(List.of());

        // Then
        assertNotNull(loans);
        assertEquals(0, loans.size());
    }

    /**
     * Test to loan list when loan entities list is null then return null.
     */
    @Test
    void testToLoanList_whenLoanEntitiesListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanEntityMapper.toLoanList(null));
    }
}
