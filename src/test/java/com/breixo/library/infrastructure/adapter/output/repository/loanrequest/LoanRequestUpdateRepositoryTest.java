package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import java.util.List;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.adapter.output.entities.LoanRequestEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.loanrequest.LoanRequestEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

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

/** The Class Loan Request Update Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestUpdateRepositoryTest {

    /** The loan request update repository. */
    @InjectMocks
    LoanRequestUpdateRepository loanRequestUpdateRepository;

    /** The loan request my batis mapper. */
    @Mock
    LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** The loan request entity mapper. */
    @Mock
    LoanRequestEntityMapper loanRequestEntityMapper;

    /**
     * Test execute when command is valid then update and return loan request.
     */
    @Test
    void testExecute_whenCommandIsValid_thenUpdateAndReturnLoanRequest() {

        // Given
        final var updateLoanRequestCommand = Instancio.create(UpdateLoanRequestCommand.class);
        final var loanRequestEntity = Instancio.create(LoanRequestEntity.class);
        final var updatedLoanRequestEntity = Instancio.create(LoanRequestEntity.class);
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(updateLoanRequestCommand.id()).build();

        // When
        when(this.loanRequestEntityMapper.toLoanRequestEntity(updateLoanRequestCommand)).thenReturn(loanRequestEntity);
        when(this.loanRequestMyBatisMapper.find(searchCriteria)).thenReturn(List.of(updatedLoanRequestEntity));
        when(this.loanRequestEntityMapper.toLoanRequest(updatedLoanRequestEntity)).thenReturn(loanRequest);
        final var result = this.loanRequestUpdateRepository.execute(updateLoanRequestCommand);

        // Then
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequestEntity(updateLoanRequestCommand);
        verify(this.loanRequestMyBatisMapper, times(1)).update(loanRequestEntity);
        verify(this.loanRequestMyBatisMapper, times(1)).find(searchCriteria);
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequest(updatedLoanRequestEntity);
        assertEquals(loanRequest, result);
    }

    /**
     * Test execute when update throws exception then throw loan request exception.
     */
    @Test
    void testExecute_whenUpdateThrowsException_thenThrowLoanRequestException() {

        // Given
        final var updateLoanRequestCommand = Instancio.create(UpdateLoanRequestCommand.class);
        final var loanRequestEntity = Instancio.create(LoanRequestEntity.class);

        // When
        when(this.loanRequestEntityMapper.toLoanRequestEntity(updateLoanRequestCommand)).thenReturn(loanRequestEntity);
        doThrow(new RuntimeException()).when(this.loanRequestMyBatisMapper).update(loanRequestEntity);
        final var loanRequestException = assertThrows(LoanRequestException.class,
                () -> this.loanRequestUpdateRepository.execute(updateLoanRequestCommand));

        // Then
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequestEntity(updateLoanRequestCommand);
        verify(this.loanRequestMyBatisMapper, times(1)).update(loanRequestEntity);
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_UPDATE_ERROR_CODE_ERROR, loanRequestException.getCode());
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_UPDATE_ERROR_MESSAGE_ERROR, loanRequestException.getMessage());
    }
}
