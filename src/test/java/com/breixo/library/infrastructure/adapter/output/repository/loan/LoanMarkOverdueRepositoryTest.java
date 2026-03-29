package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Mark Overdue Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanMarkOverdueRepositoryTest {

    /** The Loan Mark Overdue Repository. */
    @InjectMocks
    LoanMarkOverdueRepository loanMarkOverdueRepository;

    /** The Loan My Batis Mapper. */
    @Mock
    LoanMyBatisMapper loanMyBatisMapper;

    /**
     * Test mark overdue when loans are overdue then return updated count.
     */
    @Test
    void testMarkOverdue_whenLoansAreOverdue_thenReturnUpdatedCount() {
        // Given
        final var expectedCount = Instancio.create(Integer.class);

        // When
        when(this.loanMyBatisMapper.markOverdue()).thenReturn(expectedCount);
        final var result = this.loanMarkOverdueRepository.markOverdue();

        // Then
        verify(this.loanMyBatisMapper, times(1)).markOverdue();
        assertEquals(expectedCount, result);
    }

    /**
     * Test mark overdue when no loans are overdue then return zero.
     */
    @Test
    void testMarkOverdue_whenNoLoansAreOverdue_thenReturnZero() {
        // Given
        final var expectedCount = 0;

        // When
        when(this.loanMyBatisMapper.markOverdue()).thenReturn(expectedCount);
        final var result = this.loanMarkOverdueRepository.markOverdue();

        // Then
        verify(this.loanMyBatisMapper, times(1)).markOverdue();
        assertEquals(expectedCount, result);
    }
}
