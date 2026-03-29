package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.util.List;

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

/** The Class Loan Retrieval repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanRetrievalRepositoryTest {

    /** The loan retrieval repository. */
    @InjectMocks
    LoanRetrievalRepository loanRetrievalRepository;

    /** The loan my batis mapper. */
    @Mock
    LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    @Mock
    LoanEntityMapper loanEntityMapper;

    /**
     * Test find when loans exist then return loans.
     */
    @Test
    void testFind_whenLoansExist_thenReturnLoans() {
        
        // Given
        final var loanSearchCriteriaCommand = Instancio.create(LoanSearchCriteriaCommand.class);
        final var loanEntities = Instancio.createList(LoanEntity.class);
        final var loans = Instancio.createList(Loan.class);

        // When
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(loanEntities);
        when(this.loanEntityMapper.toLoanList(loanEntities)).thenReturn(loans);
        final var result = this.loanRetrievalRepository.find(loanSearchCriteriaCommand);

        // Then
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanEntityMapper, times(1)).toLoanList(loanEntities);
        assertEquals(loans, result);
    }

    /**
     * Test find when no loans match criteria then return empty list.
     */
    @Test
    void testFind_whenNoLoansMatchCriteria_thenReturnEmptyList() {
        
        // Given
        final var loanSearchCriteriaCommand = Instancio.create(LoanSearchCriteriaCommand.class);
        final var loans = List.<Loan>of();

        // When
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(List.of());
        when(this.loanEntityMapper.toLoanList(List.of())).thenReturn(loans);
        final var result = this.loanRetrievalRepository.find(loanSearchCriteriaCommand);

        // Then
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanEntityMapper, times(1)).toLoanList(List.of());
        assertTrue(result.isEmpty());
    }
}
