package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import java.util.List;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.adapter.output.entities.LoanRequestEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.LoanRequestEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

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

/** The Class Loan Request Creation Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestCreationRepositoryTest {

    /** The loan request creation repository. */
    @InjectMocks
    LoanRequestCreationRepository loanRequestCreationRepository;

    /** The loan request my batis mapper. */
    @Mock
    LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** The loan request entity mapper. */
    @Mock
    LoanRequestEntityMapper loanRequestEntityMapper;

    /**
     * Test execute when command is valid then create and return loan request.
     */
    @Test
    void testExecute_whenCommandIsValid_thenCreateAndReturnLoanRequest() {

        // Given
        final var createLoanRequestCommand = Instancio.create(CreateLoanRequestCommand.class);
        final var loanRequestEntity = Instancio.create(LoanRequestEntity.class);
        final var createdLoanRequestEntity = Instancio.create(LoanRequestEntity.class);
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(loanRequestEntity.getId()).build();

        // When
        when(this.loanRequestEntityMapper.toLoanRequestEntity(createLoanRequestCommand)).thenReturn(loanRequestEntity);
        when(this.loanRequestMyBatisMapper.find(searchCriteria)).thenReturn(List.of(createdLoanRequestEntity));
        when(this.loanRequestEntityMapper.toLoanRequest(createdLoanRequestEntity)).thenReturn(loanRequest);
        final var result = this.loanRequestCreationRepository.execute(createLoanRequestCommand);

        // Then
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequestEntity(createLoanRequestCommand);
        verify(this.loanRequestMyBatisMapper, times(1)).insert(loanRequestEntity);
        verify(this.loanRequestMyBatisMapper, times(1)).find(searchCriteria);
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequest(createdLoanRequestEntity);
        assertEquals(loanRequest, result);
    }
}
