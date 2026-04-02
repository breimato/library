package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import java.util.List;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Request Retrieval Repository Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestRetrievalRepositoryTest {

    /** The loan request retrieval repository. */
    @InjectMocks
    LoanRequestRetrievalRepository loanRequestRetrievalRepository;

    /** The loan request my batis mapper. */
    @Mock
    LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** The loan request entity mapper. */
    @Mock
    LoanRequestEntityMapper loanRequestEntityMapper;

    /**
     * Test find when no criteria then return all loan requests.
     */
    @Test
    void testFind_whenNoCriteria_thenReturnAllLoanRequests() {

        // Given
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().build();
        final var loanRequestEntities = Instancio.createList(LoanRequestEntity.class);
        final var loanRequests = Instancio.createList(LoanRequest.class);

        // When
        when(this.loanRequestMyBatisMapper.find(searchCriteria)).thenReturn(loanRequestEntities);
        when(this.loanRequestEntityMapper.toLoanRequestList(loanRequestEntities)).thenReturn(loanRequests);
        final var result = this.loanRequestRetrievalRepository.find(searchCriteria);

        // Then
        verify(this.loanRequestMyBatisMapper, times(1)).find(searchCriteria);
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequestList(loanRequestEntities);
        assertEquals(loanRequests, result);
    }

    /**
     * Test find when no loan requests exist then return empty list.
     */
    @Test
    void testFind_whenNoLoanRequestsExist_thenReturnEmptyList() {

        // Given
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().build();
        final var loanRequests = List.<LoanRequest>of();

        // When
        when(this.loanRequestMyBatisMapper.find(searchCriteria)).thenReturn(List.of());
        when(this.loanRequestEntityMapper.toLoanRequestList(List.of())).thenReturn(loanRequests);
        final var result = this.loanRequestRetrievalRepository.find(searchCriteria);

        // Then
        verify(this.loanRequestMyBatisMapper, times(1)).find(searchCriteria);
        verify(this.loanRequestEntityMapper, times(1)).toLoanRequestList(List.of());
        assertTrue(result.isEmpty());
    }

    /**
     * Test find by id when loan request exists then return loan request.
     */
    @Test
    void testFindById_whenLoanRequestExists_thenReturnLoanRequest() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(id).build();
        final var loanRequestEntity = Instancio.create(LoanRequestEntity.class);
        final var loanRequest = Instancio.create(LoanRequest.class);

        // When
        when(this.loanRequestMyBatisMapper.find(searchCriteria)).thenReturn(List.of(loanRequestEntity));
        when(this.loanRequestEntityMapper.toLoanRequestList(List.of(loanRequestEntity))).thenReturn(List.of(loanRequest));
        final var result = this.loanRequestRetrievalRepository.findById(id);

        // Then
        assertEquals(loanRequest, result);
    }

    /**
     * Test find by id when loan request not found then throw loan request exception.
     */
    @Test
    void testFindById_whenLoanRequestNotFound_thenThrowLoanRequestException() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(id).build();

        // When
        when(this.loanRequestMyBatisMapper.find(searchCriteria)).thenReturn(List.of());
        when(this.loanRequestEntityMapper.toLoanRequestList(List.of())).thenReturn(List.of());
        final var exception = assertThrows(LoanRequestException.class,
                () -> this.loanRequestRetrievalRepository.findById(id));

        // Then
        assertEquals(ExceptionMessageConstants.LOAN_REQUEST_NOT_FOUND_MESSAGE_ERROR, exception.getMessage());
    }
}
