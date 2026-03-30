package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1;

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

    /** The fine mapper. */
    @Mock
    FineMapper fineMapper;

    /**
     * Test to fine V1 response when fine is valid then return mapped response.
     */
    @Test
    void testToFineV1Response_whenFineIsValid_thenReturnMappedResponse() {

        // Given
        final var fine = Instancio.create(Fine.class);
        final var fineV1 = Instancio.create(FineV1.class);

        // When
        when(this.fineMapper.toFineV1(fine)).thenReturn(fineV1);
        final var fineV1Response = this.fineResponseMapper.toFineV1Response(fine);

        // Then
        verify(this.fineMapper, times(1)).toFineV1(fine);
        assertNotNull(fineV1Response);
        assertEquals(fineV1, fineV1Response.getFine());
    }

    /**
     * Test to fine V1 response when fine is null then return null.
     */
    @Test
    void testToFineV1Response_whenFineIsNull_thenReturnNull() {
        // When / Then
        assertNull(this.fineResponseMapper.toFineV1Response(null));
    }
}
