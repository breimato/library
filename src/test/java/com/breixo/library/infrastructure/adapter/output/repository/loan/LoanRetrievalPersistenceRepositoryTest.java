package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.infrastructure.adapter.output.entities.LoanEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.LoanEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Retrieval Persistence Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanRetrievalPersistenceRepositoryTest {

    /** The loan retrieval persistence repository. */
    @InjectMocks
    LoanRetrievalPersistenceRepository loanRetrievalPersistenceRepository;

    /** The loan my batis mapper. */
    @Mock
    LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    @Mock
    LoanEntityMapper loanEntityMapper;

    /**
     * Test find when loan found then return loan.
     */
    @Test
    void testFind_whenLoanFound_thenReturnLoan() {
        // Given
        final var loanSearchCriteriaCommand = Instancio.create(LoanSearchCriteriaCommand.class);
        final var loanEntity = Instancio.create(LoanEntity.class);
        final var loan = Instancio.create(Loan.class);

        // When
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(List.of(loanEntity));
        when(this.loanEntityMapper.toLoan(loanEntity)).thenReturn(loan);

        final var result = this.loanRetrievalPersistenceRepository.find(loanSearchCriteriaCommand);

        // Then
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanEntityMapper, times(1)).toLoan(loanEntity);

        assertEquals(Optional.of(loan), result);
    }

    /**
     * Test find when loan not found then return empty optional.
     */
    @Test
    void testFind_whenLoanNotFound_thenReturnEmptyOptional() {
        // Given
        final var loanSearchCriteriaCommand = Instancio.create(LoanSearchCriteriaCommand.class);

        // When
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(List.of());
        final var result = this.loanRetrievalPersistenceRepository.find(loanSearchCriteriaCommand);

        // Then
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        assertTrue(result.isEmpty());
    }

    /**
     * Test find all when loans exist then return loans.
     */
    @Test
    void testFindAll_whenLoansExist_thenReturnLoans() {
        // Given
        final var loanSearchCriteriaCommand = Instancio.create(LoanSearchCriteriaCommand.class);
        final var loanEntities = Instancio.createList(LoanEntity.class);
        final var loans = Instancio.createList(Loan.class);

        // When
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(loanEntities);
        when(this.loanEntityMapper.toLoanList(loanEntities)).thenReturn(loans);
        final var result = this.loanRetrievalPersistenceRepository.findAll(loanSearchCriteriaCommand);

        // Then
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanEntityMapper, times(1)).toLoanList(loanEntities);
        assertEquals(loans, result);
    }
}
