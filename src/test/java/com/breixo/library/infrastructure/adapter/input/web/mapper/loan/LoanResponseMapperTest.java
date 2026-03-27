package com.breixo.library.infrastructure.adapter.input.web.mapper.loan;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1Dto;

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

/** The Class Loan Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanResponseMapperTest {

    /** The loan response mapper. */
    @InjectMocks
    LoanResponseMapperImpl loanResponseMapper;

    /** The loan mapper. */
    @Mock
    LoanMapper loanMapper;

    /**
     * Test to loan V1 response when loan is valid then return mapped response.
     */
    @Test
    void testToLoanV1Response_whenLoanIsValid_thenReturnMappedResponse() {

        // Given
        final var loan = Instancio.create(Loan.class);
        final var loanV1Dto = Instancio.create(LoanV1Dto.class);

        // When
        when(this.loanMapper.toLoanV1(loan)).thenReturn(loanV1Dto);

        final var loanV1ResponseDto = this.loanResponseMapper.toLoanV1Response(loan);

        // Then
        verify(this.loanMapper, times(1)).toLoanV1(loan);
        assertNotNull(loanV1ResponseDto);
        assertEquals(loanV1Dto, loanV1ResponseDto.getLoan());
    }

    /**
     * Test to loan V1 response when loan is null then return null.
     */
    @Test
    void testToLoanV1Response_whenLoanIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanResponseMapper.toLoanV1Response(null));
    }
}
