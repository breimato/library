package com.breixo.library.infrastructure.adapter.input.web.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** The Class Date Mapper Test. */
@ExtendWith(MockitoExtension.class)
class DateMapperTest {

    /** The date mapper. */
    @InjectMocks
    DateMapperImpl dateMapper;

    /**
     * Test to offset date time when local date time is valid then return offset date time.
     */
    @Test
    void testToOffsetDateTime_whenLocalDateTimeIsValid_thenReturnOffsetDateTime() {
        // Given
        final var localDateTime = LocalDateTime.now();

        // When / Then
        assertEquals(localDateTime.atOffset(ZoneOffset.UTC), this.dateMapper.toOffsetDateTime(localDateTime));
    }
}
