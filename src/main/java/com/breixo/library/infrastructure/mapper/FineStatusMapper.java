package com.breixo.library.infrastructure.mapper;

import java.util.Objects;

import com.breixo.library.domain.model.fine.enums.FineStatus;

import org.mapstruct.Mapper;

/** The Interface Fine Status Mapper. */
@Mapper(componentModel = "spring")
public interface FineStatusMapper {

    /**
     * To status id.
     *
     * @param fineStatus the fine status.
     * @return the integer.
     */
    default Integer toStatusId(final FineStatus fineStatus) {
        if (Objects.isNull(fineStatus)) {
            return null;
        }
        return fineStatus.getId();
    }

    /**
     * To fine status.
     *
     * @param id the id.
     * @return the fine status.
     */
    default FineStatus toFineStatus(final Integer id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return FineStatus.values()[id];
    }
}
