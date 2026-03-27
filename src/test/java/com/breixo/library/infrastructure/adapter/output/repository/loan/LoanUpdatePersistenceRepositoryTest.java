package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.UpdateLoanReturnCommand;
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

/** The Class Loan Update Persistence Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanUpdatePersistenceRepositoryTest {

    /** The loan update persistence repository. */
    @InjectMocks
    LoanUpdatePersistenceRepository loanUpdatePersistenceRepository;

    /** The loan my batis mapper. */
    @Mock
    LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    @Mock
    LoanEntityMapper loanEntityMapper;

    /**
     * Test execute when command is valid then return updated loan.
     */
    @Test
    void testExecute_whenCommandIsValid_thenReturnUpdatedLoan() {

        // Given
        final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);
        final var loanEntity = Instancio.create(LoanEntity.class);
        final var loan = Instancio.create(Loan.class);
        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(updateLoanReturnCommand.id()).build();

        // When
        when(this.loanMyBatisMapper.find(loanSearchCriteriaCommand)).thenReturn(List.of(loanEntity));
        when(this.loanEntityMapper.toLoan(loanEntity)).thenReturn(loan);

        final var result = this.loanUpdatePersistenceRepository.execute(updateLoanReturnCommand);

        // Then
        verify(this.loanMyBatisMapper, times(1)).update(updateLoanReturnCommand);
        verify(this.loanMyBatisMapper, times(1)).find(loanSearchCriteriaCommand);
        verify(this.loanEntityMapper, times(1)).toLoan(loanEntity);

        assertEquals(loan, result);
    }

    /**
     * Test execute when update throws exception then throw loan exception.
     */
    @Test
    void testExecute_whenUpdateThrowsException_thenThrowLoanException() {

        // Given
        final var updateLoanReturnCommand = Instancio.create(UpdateLoanReturnCommand.class);

        // When
        doThrow(new RuntimeException()).when(this.loanMyBatisMapper).update(updateLoanReturnCommand);

        final var loanException = assertThrows(LoanException.class,
                () -> this.loanUpdatePersistenceRepository.execute(updateLoanReturnCommand));

        // Then
        verify(this.loanMyBatisMapper, times(1)).update(updateLoanReturnCommand);

        assertEquals(ExceptionMessageConstants.LOAN_UPDATE_ERROR_CODE_ERROR, loanException.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_UPDATE_ERROR_MESSAGE_ERROR, loanException.getMessage());
    }
}
