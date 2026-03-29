package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Creation Persistence Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanCreationRepositoryTest {

    /** The loan creation persistence repository. */
    @InjectMocks
    LoanCreationRepository loanCreationRepository;

    /** The loan my batis mapper. */
    @Mock
    LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    @Mock
    LoanEntityMapper loanEntityMapper;

    /**
     * Test execute when command is valid then return created loan.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnCreatedLoan() {

        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var loanEntity = Instancio.create(LoanEntity.class);
        final var createdLoanEntity = Instancio.create(LoanEntity.class);
        final var loan = Instancio.create(Loan.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(loanEntity.getId()).build();

        // When
        when(this.loanEntityMapper.toLoanEntity(createLoanCommand)).thenReturn(loanEntity);
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(List.of(createdLoanEntity));
        when(this.loanEntityMapper.toLoan(createdLoanEntity)).thenReturn(loan);

        final var result = this.loanCreationRepository.execute(createLoanCommand);

        // Then
        verify(this.loanEntityMapper, times(1)).toLoanEntity(createLoanCommand);
        verify(this.loanMyBatisMapper, times(1)).insert(loanEntity);
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanEntityMapper, times(1)).toLoan(createdLoanEntity);

        assertEquals(loan, result);
    }

    /**
     * Test execute when insert throws exception then throw loan exception.
     */
    @Test
    void testExecute_whenInsertThrowsException_thenThrowLoanException() {
        // Given
        final var createLoanCommand = Instancio.create(CreateLoanCommand.class);
        final var loanEntity = Instancio.create(LoanEntity.class);

        // When
        when(this.loanEntityMapper.toLoanEntity(createLoanCommand)).thenReturn(loanEntity);
        doThrow(new RuntimeException()).when(this.loanMyBatisMapper).insert(loanEntity);
        final var loanException = assertThrows(LoanException.class,
                () -> this.loanCreationRepository.execute(createLoanCommand));

        // Then
        verify(this.loanEntityMapper, times(1)).toLoanEntity(createLoanCommand);
        verify(this.loanMyBatisMapper, times(1)).insert(loanEntity);
        assertEquals(ExceptionMessageConstants.LOAN_CREATION_ERROR_CODE_ERROR, loanException.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_CREATION_ERROR_MESSAGE_ERROR, loanException.getMessage());
    }
}
