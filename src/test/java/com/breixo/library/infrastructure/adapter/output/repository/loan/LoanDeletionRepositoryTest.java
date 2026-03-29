package com.breixo.library.infrastructure.adapter.output.repository.loan;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** The Class Loan Deletion repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanDeletionRepositoryTest {

    /** The loan deletion repository. */
    @InjectMocks
    LoanDeletionRepository loanDeletionRepository;

    /** The loan my batis mapper. */
    @Mock
    LoanMyBatisMapper loanMyBatisMapper;

    /**
     * Test execute when id is valid then delete loan.
     */
    @Test
    void testExecute_whenIdIsValid_thenDeleteLoan() {
        
        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.loanDeletionRepository.execute(id);

        // Then
        verify(this.loanMyBatisMapper, times(1)).delete(id);
    }
}
