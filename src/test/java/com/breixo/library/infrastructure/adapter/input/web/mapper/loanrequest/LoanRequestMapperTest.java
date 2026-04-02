package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import java.util.List;

import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.mapper.LoanRequestStatusMapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** The Class Loan Request Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestMapperTest {

    /** The loan request mapper. */
    @InjectMocks
    LoanRequestMapperImpl loanRequestMapper;

    /** The loan request status mapper. */
    @Mock
    LoanRequestStatusMapper loanRequestStatusMapper;

    /**
     * Test to loan request V1 when loan request is valid then return mapped dto.
     */
    @Test
    void testToLoanRequestV1_whenLoanRequestIsValid_thenReturnMappedDto() {

        // Given
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var statusId = Instancio.create(Integer.class);

        // When
        when(this.loanRequestStatusMapper.toStatusId(loanRequest.status())).thenReturn(statusId);
        final var loanRequestV1 = this.loanRequestMapper.toLoanRequestV1(loanRequest);

        // Then
        verify(this.loanRequestStatusMapper, times(1)).toStatusId(loanRequest.status());
        assertNotNull(loanRequestV1);
        assertEquals(loanRequest.id(), loanRequestV1.getId());
        assertEquals(loanRequest.userId(), loanRequestV1.getUserId());
        assertEquals(loanRequest.bookId(), loanRequestV1.getBookId());
        assertEquals(statusId, loanRequestV1.getStatus());
        assertEquals(loanRequest.rejectionReason(), loanRequestV1.getRejectionReason());
    }

    /**
     * Test to loan request V1 when loan request is null then return null.
     */
    @Test
    void testToLoanRequestV1_whenLoanRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestMapper.toLoanRequestV1(null));
    }

    /**
     * Test to loan request V1 list when loan requests are valid then return mapped dto list.
     */
    @Test
    void testToLoanRequestV1List_whenLoanRequestsAreValid_thenReturnMappedDtoList() {

        // Given
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var loanRequests = List.of(loanRequest);
        final var statusId = Instancio.create(Integer.class);

        // When
        when(this.loanRequestStatusMapper.toStatusId(loanRequest.status())).thenReturn(statusId);
        final var loanRequestV1List = this.loanRequestMapper.toLoanRequestV1List(loanRequests);

        // Then
        assertNotNull(loanRequestV1List);
        assertEquals(1, loanRequestV1List.size());
        assertEquals(loanRequest.id(), loanRequestV1List.getFirst().getId());
    }

    /**
     * Test to loan request V1 list when list is null then return null.
     */
    @Test
    void testToLoanRequestV1List_whenListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestMapper.toLoanRequestV1List(null));
    }

    /**
     * Test to loan request V1 list when list is empty then return empty list.
     */
    @Test
    void testToLoanRequestV1List_whenListIsEmpty_thenReturnEmptyList() {
        // When
        final var loanRequestV1List = this.loanRequestMapper.toLoanRequestV1List(List.of());

        // Then
        assertNotNull(loanRequestV1List);
        assertEquals(0, loanRequestV1List.size());
    }
}
