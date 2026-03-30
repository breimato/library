package com.breixo.library.infrastructure.adapter.input.web.mapper.fine;

import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.infrastructure.adapter.input.web.dto.FineV1Response;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** The Interface Fine Response Mapper. */
@Mapper(componentModel = "spring", uses = FineMapper.class)
public interface FineResponseMapper {

    /**
     * To fine V1 response.
     *
     * @param fine the fine.
     * @return the fine V1 response.
     */
    @Mapping(target = "fine", source = "fine")
    FineV1Response toFineV1Response(Fine fine);
}
