package com.breixo.library.infrastructure.adapter.input.web.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/** The Interface Date Mapper. */
@Mapper(componentModel = "spring")
public interface DateMapper {

    /**
     * To offset date time.
     *
     * @param localDateTime the local date time.
     * @return the offset date time.
     */
    default OffsetDateTime toOffsetDateTime(final LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}
