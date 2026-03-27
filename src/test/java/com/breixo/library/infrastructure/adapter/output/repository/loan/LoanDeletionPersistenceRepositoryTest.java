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

/** The Class Loan Deletion Persistence Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanDeletionPersistenceRepositoryTest {

    /** The loan deletion persistence repository. */
    @InjectMocks
    LoanDeletionPersistenceRepository loanDeletionPersistenceRepository;

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
        this.loanDeletionPersistenceRepository.execute(id);

        // Then
        verify(this.loanMyBatisMapper, times(1)).delete(id);
    }
}
