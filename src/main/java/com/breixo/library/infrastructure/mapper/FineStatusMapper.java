package com.breixo.library.infrastructure.mapper;

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
        return fineStatus.getId();
    }

    /**
     * To fine status.
     *
     * @param id the id.
     * @return the fine status.
     */
    default FineStatus toFineStatus(final Integer id) {
        return FineStatus.values()[id];
    }
}
