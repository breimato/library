package com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest;

import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1Response;

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

/** The Class Loan Request Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class LoanRequestResponseMapperTest {

    /** The loan request response mapper. */
    @InjectMocks
    LoanRequestResponseMapperImpl loanRequestResponseMapper;

    /** The loan request mapper. */
    @Mock
    LoanRequestMapper loanRequestMapper;

    /**
     * Test to loan request V1 response when loan request is valid then return mapped response.
     */
    @Test
    void testToLoanRequestV1Response_whenLoanRequestIsValid_thenReturnMappedResponse() {

        // Given
        final var loanRequest = Instancio.create(LoanRequest.class);
        final var loanRequestV1 = Instancio.create(LoanRequestV1.class);

        // When
        when(this.loanRequestMapper.toLoanRequestV1(loanRequest)).thenReturn(loanRequestV1);
        final var loanRequestV1Response = this.loanRequestResponseMapper.toLoanRequestV1Response(loanRequest);

        // Then
        verify(this.loanRequestMapper, times(1)).toLoanRequestV1(loanRequest);
        assertNotNull(loanRequestV1Response);
        assertEquals(loanRequestV1, loanRequestV1Response.getLoanRequest());
    }

    /**
     * Test to loan request V1 response when loan request is null then return null.
     */
    @Test
    void testToLoanRequestV1Response_whenLoanRequestIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.loanRequestResponseMapper.toLoanRequestV1Response(null));
    }
}
