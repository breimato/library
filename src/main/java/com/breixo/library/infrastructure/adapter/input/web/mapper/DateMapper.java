package com.breixo.library.infrastructure.adapter.input.web.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

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
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

    /**
     * To local date time.
     *
     * @param offsetDateTime the offset date time.
     * @return the local date time.
     */
    default LocalDateTime toLocalDateTime(final OffsetDateTime offsetDateTime) {
        if (Objects.isNull(offsetDateTime)) {
            return null;
        }
        return offsetDateTime.toLocalDateTime();
    }
}
