package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.infrastructure.adapter.input.web.mapper.DateMapper;
import com.breixo.library.infrastructure.mapper.FineStatusMapper;

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

/** The Class Fine Response Mapper Test. */
@ExtendWith(MockitoExtension.class)
class FineResponseMapperTest {

    /** The fine response mapper. */
    @InjectMocks
    FineResponseMapperImpl fineResponseMapper;

    /** The fine status mapper. */
    @Mock
    FineStatusMapper fineStatusMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to fine V 1 response when fine is valid then return mapped response.
     */
    @Test
    void testToFineV1Response_whenFineIsValid_thenReturnMappedResponse() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var loanId = Instancio.create(Integer.class);
        final var amountEuros = Instancio.create(BigDecimal.class);
        final var paidAt = Instancio.create(LocalDateTime.class);
        final var statusId = Instancio.create(Integer.class);
        final var offsetDateTime = Instancio.create(OffsetDateTime.class);
        final var fine = Fine.builder()
                .id(id)
                .loanId(loanId)
                .amountEuros(amountEuros)
                .status(FineStatus.PAID)
                .paidAt(paidAt)
                .build();

        // When
        when(this.fineStatusMapper.toStatusId(FineStatus.PAID)).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(paidAt)).thenReturn(offsetDateTime);
        final var fineV1Response = this.fineResponseMapper.toFineV1Response(fine);

        // Then
        verify(this.fineStatusMapper, times(1)).toStatusId(FineStatus.PAID);
        verify(this.dateMapper, times(1)).toOffsetDateTime(paidAt);
        assertNotNull(fineV1Response);
        assertNotNull(fineV1Response.getFine());
        assertEquals(id, fineV1Response.getFine().getId());
        assertEquals(loanId, fineV1Response.getFine().getLoanId());
        assertEquals(amountEuros.doubleValue(), fineV1Response.getFine().getAmountEuros());
        assertEquals(statusId, fineV1Response.getFine().getStatus());
        assertEquals(offsetDateTime, fineV1Response.getFine().getPaidAt());
    }

    /**
     * Test to fine V 1 response when amount euros is null then return mapped response without amount.
     */
    @Test
    void testToFineV1Response_whenAmountEurosIsNull_thenReturnMappedResponseWithoutAmount() {

        // Given
        final var id = Instancio.create(Integer.class);
        final var loanId = Instancio.create(Integer.class);
        final var paidAt = Instancio.create(LocalDateTime.class);
        final var statusId = Instancio.create(Integer.class);
        final var offsetDateTime = Instancio.create(OffsetDateTime.class);
        final var fine = Fine.builder()
                .id(id)
                .loanId(loanId)
                .amountEuros(null)
                .status(FineStatus.WAIVED)
                .paidAt(paidAt)
                .build();

        // When
        when(this.fineStatusMapper.toStatusId(FineStatus.WAIVED)).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(paidAt)).thenReturn(offsetDateTime);
        final var fineV1Response = this.fineResponseMapper.toFineV1Response(fine);

        // Then
        verify(this.fineStatusMapper, times(1)).toStatusId(FineStatus.WAIVED);
        verify(this.dateMapper, times(1)).toOffsetDateTime(paidAt);
        assertNotNull(fineV1Response);
        assertNull(fineV1Response.getFine().getAmountEuros());
    }

    /**
     * Test to fine V 1 response when fine is null then return null.
     */
    @Test
    void testToFineV1Response_whenFineIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineResponseMapper.toFineV1Response(null));
    }

    /**
     * Test fine to fine V 1 when fine is null then return null.
     */
    @Test
    void testFineToFineV1_whenFineIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineResponseMapper.fineToFineV1(null));
    }
}
