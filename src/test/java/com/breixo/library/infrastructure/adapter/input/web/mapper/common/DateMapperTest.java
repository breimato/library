package com.breixo.library.infrastructure.adapter.input.web.mapper.common;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        final var localDateTime = Instancio.create(LocalDateTime.class);

        // When / Then
        assertEquals(localDateTime.atOffset(ZoneOffset.UTC), this.dateMapper.toOffsetDateTime(localDateTime));
    }

    /**
     * Test to local date time when offset date time is valid then return local date time.
     */
    @Test
    void testToLocalDateTime_whenOffsetDateTimeIsValid_thenReturnLocalDateTime() {

        // Given
        final var offsetDateTime = Instancio.create(OffsetDateTime.class);

        // When / Then
        assertEquals(offsetDateTime.toLocalDateTime(), this.dateMapper.toLocalDateTime(offsetDateTime));
    }

    /**
     * Test to offset date time when local date time is null then return null.
     */
    @Test
    void testToOffsetDateTime_whenLocalDateTimeIsNull_thenReturnNull() {

        // When / Then
        assertNull(this.dateMapper.toOffsetDateTime(null));
    }

    /**
     * Test to local date time when offset date time is null then return null.
     */
    @Test
    void testToLocalDateTime_whenOffsetDateTimeIsNull_thenReturnNull() {

        // When / Then
        assertNull(this.dateMapper.toLocalDateTime(null));
    }
}
