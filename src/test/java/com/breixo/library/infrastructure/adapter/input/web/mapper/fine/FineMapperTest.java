package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

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

/** The Class Fine Mapper Test. */
@ExtendWith(MockitoExtension.class)
class FineMapperTest {

    /** The fine mapper. */
    @InjectMocks
    FineMapperImpl fineMapper;

    /** The fine status mapper. */
    @Mock
    FineStatusMapper fineStatusMapper;

    /** The date mapper. */
    @Mock
    DateMapper dateMapper;

    /**
     * Test to fine V 1 when fine is valid then return mapped dto.
     */
    @Test
    void testToFineV1_whenFineIsValid_thenReturnMappedDto() {

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
                .status(FineStatus.PENDING)
                .paidAt(paidAt)
                .build();

        // When
        when(this.fineStatusMapper.toStatusId(FineStatus.PENDING)).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(paidAt)).thenReturn(offsetDateTime);
        final var fineV1 = this.fineMapper.toFineV1(fine);

        // Then
        verify(this.fineStatusMapper, times(1)).toStatusId(FineStatus.PENDING);
        verify(this.dateMapper, times(1)).toOffsetDateTime(paidAt);
        assertNotNull(fineV1);
        assertEquals(id, fineV1.getId());
        assertEquals(loanId, fineV1.getLoanId());
        assertEquals(amountEuros.doubleValue(), fineV1.getAmountEuros());
        assertEquals(statusId, fineV1.getStatus());
        assertEquals(offsetDateTime, fineV1.getPaidAt());
    }

    /**
     * Test to fine V 1 when amount euros is null then return mapped dto without amount.
     */
    @Test
    void testToFineV1_whenAmountEurosIsNull_thenReturnMappedDtoWithoutAmount() {

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
        final var fineV1 = this.fineMapper.toFineV1(fine);

        // Then
        verify(this.fineStatusMapper, times(1)).toStatusId(FineStatus.WAIVED);
        verify(this.dateMapper, times(1)).toOffsetDateTime(paidAt);
        assertNotNull(fineV1);
        assertNull(fineV1.getAmountEuros());
        assertEquals(statusId, fineV1.getStatus());
    }

    /**
     * Test to fine V 1 when fine is null then return null.
     */
    @Test
    void testToFineV1_whenFineIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineMapper.toFineV1(null));
    }

    /**
     * Test to fine V1 list when fines are valid then return mapped dto list.
     */
    @Test
    void testToFineV1List_whenFinesAreValid_thenReturnMappedDtoList() {

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
                .status(FineStatus.PENDING)
                .paidAt(paidAt)
                .build();
        final var fines = List.of(fine);

        // When
        when(this.fineStatusMapper.toStatusId(FineStatus.PENDING)).thenReturn(statusId);
        when(this.dateMapper.toOffsetDateTime(paidAt)).thenReturn(offsetDateTime);
        final var fineV1List = this.fineMapper.toFineV1List(fines);

        // Then
        assertNotNull(fineV1List);
        assertEquals(1, fineV1List.size());
        assertEquals(id, fineV1List.getFirst().getId());
    }

    /**
     * Test to fine V1 list when fines list is empty then return empty list.
     */
    @Test
    void testToFineV1List_whenFinesListIsEmpty_thenReturnEmptyList() {
        // When
        final var fineV1List = this.fineMapper.toFineV1List(List.of());

        // Then
        assertNotNull(fineV1List);
        assertEquals(0, fineV1List.size());
    }

    /**
     * Test to fine V1 list when fines list is null then return null.
     */
    @Test
    void testToFineV1List_whenFinesListIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineMapper.toFineV1List(null));
    }
}
