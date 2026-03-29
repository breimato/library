package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import java.util.List;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.infrastructure.mapper.LoanStatusMapper;

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

/** The Class Loan Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanMapperTest {

    /** The loan mapper. */
    @InjectMocks
    LoanMapperImpl loanMapper;

    /** The loan status mapper. */
    @Mock
    LoanStatusMapper loanStatusMapper;

    /**
     * Test to loan V1 when loan is valid then return mapped dto.
     */
    @Test
    void testToLoanV1_whenLoanIsValid_thenReturnMappedDto() {
        
        // Given
        final var loan = Instancio.create(Loan.class);
        final var statusId = Instancio.create(Integer.class);

        // When
        when(this.loanStatusMapper.toStatusId(loan.status())).thenReturn(statusId);
        final var loanV1Dto = this.loanMapper.toLoanV1(loan);

        // Then
        verify(this.loanStatusMapper, times(1)).toStatusId(loan.status());
        assertNotNull(loanV1Dto);
        assertEquals(loan.id(), loanV1Dto.getId());
        assertEquals(loan.userId(), loanV1Dto.getUserId());
        assertEquals(loan.bookId(), loanV1Dto.getBookId());
        assertEquals(loan.dueDate(), loanV1Dto.getDueDate());
        assertEquals(loan.returnDate(), loanV1Dto.getReturnDate());
        assertEquals(statusId, loanV1Dto.getStatus());
    }

    /**
     * Test to loan V1 when loan is null then return null.
     */
    @Test
    void testToLoanV1_whenLoanIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanMapper.toLoanV1(null));
    }

    /**
     * Test to loan V1 list when loans are valid then return mapped dto list.
     */
    @Test
    void testToLoanV1List_whenLoansAreValid_thenReturnMappedDtoList() {
        
        // Given
        final var loan = Instancio.create(Loan.class);
        final var loans = List.of(loan);
        final var statusId = Instancio.create(Integer.class);

        // When
        when(this.loanStatusMapper.toStatusId(loan.status())).thenReturn(statusId);
        final var loanV1DtoList = this.loanMapper.toLoanV1List(loans);

        // Then
        assertNotNull(loanV1DtoList);
        assertEquals(1, loanV1DtoList.size());
        assertEquals(loan.id(), loanV1DtoList.getFirst().getId());
    }

    /**
     * Test to loan V1 list when loans list is null then return null.
     */
    @Test
    void testToLoanV1List_whenLoansListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanMapper.toLoanV1List(null));
    }

    /**
     * Test to loan V1 list when loans list is empty then return empty list.
     */
    @Test
    void testToLoanV1List_whenLoansListIsEmpty_thenReturnEmptyList() {
        // When
        final var loanV1DtoList = this.loanMapper.toLoanV1List(List.of());

        // Then
        assertNotNull(loanV1DtoList);
        assertEquals(0, loanV1DtoList.size());
    }
}
