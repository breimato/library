package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/** The Class Loan Request Deletion Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestDeletionRepositoryTest {

    /** The loan request deletion repository. */
    @InjectMocks
    LoanRequestDeletionRepository loanRequestDeletionRepository;

    /** The loan request my batis mapper. */
    @Mock
    LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /**
     * Test execute when called then delete loan request.
     */
    @Test
    void testExecute_whenCalled_thenDeleteLoanRequest() {

        // Given
        final var id = Instancio.create(Integer.class);

        // When
        this.loanRequestDeletionRepository.execute(id);

        // Then
        verify(this.loanRequestMyBatisMapper, times(1)).delete(id);
    }
}
